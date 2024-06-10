package com.sparta.areadevelopment.exception;


import jakarta.transaction.NotSupportedException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.management.ServiceNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;


// AOP
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * <p>컨트롤러에 @Valid 어노테이션으로 인한 입력받은 JSON 데이터를 RequestDto 로
     * 바인딩하다가 발생한 오류들의 오류메시지들을 전부 출력해줍니다.</p>
     *
     * @param e 메서드의 인수가 올바르지 않을 때 발생하는 익셉션
     * @return 오류메세지를 JSON 형식으로 반환해줍니다.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException e) {
        List<String> errors = new ArrayList<>();

        for (FieldError error : e.getBindingResult().getFieldErrors()) {
            errors.add(error.getDefaultMessage());
        }

        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleDataIntegrityViolations(
            DataIntegrityViolationException ex) {
        // 데이터베이스 에러 메시지를 분석하여 필요한 정보를 추출
        String errorMessage = "Data integrity violation occurred.";
        if (ex.getCause() != null && ex.getCause().getCause() != null) {
            String causeMessage = ex.getCause().getCause().getMessage();
            errorMessage = parseErrorMessage(causeMessage);
        }

        Map<String, String> response = new HashMap<>();
        response.put("error", "Data Integrity Violation");
        response.put("message", errorMessage);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * 서비스에서 지원하지 않는 기능을 요청했을때 발생합니다.
     *
     * @param e 익셉션의 발생시 작성한 오류 메세지
     * @return 상태코드 400을 보내고 바디에 오류메세지를 보냅니다.
     */
    @ExceptionHandler(NotSupportedException.class)
    public ResponseEntity<Object> handleNotSupportedException(NotSupportedException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    /**
     * 메서드에 전달된 매개변수 값이 잘못되어 찾을 수 없을때 발생합니다.
     *
     * @param e 익셉션의 발생시 작성한 오류 메세지
     * @return 상태코드 400을 보내고 바디에 오류메세지를 보냅니다.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }


    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(NullPointerException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }


    // 게시글 없으면 status 200 ok
    @ExceptionHandler(ServiceNotFoundException.class)
    public ResponseEntity<Object> handleServiceNotFoundException(ServiceNotFoundException e) {
        return ResponseEntity.ok().body(e.getMessage());
    }

    // 잘못된 URL 입력에 의한 처리
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex) {
        return new ResponseEntity<>(
                "The requested URL was not found on the server. If you entered the URL manually please check your spelling and try again.",
                HttpStatus.BAD_REQUEST);
    }

    // 데이터베이스 오류 메시지를 파싱하여 좀 더 친절한 메시지를 반환
    private String parseErrorMessage(String dbErrorMessage) {
        if (dbErrorMessage.contains("duplicate key value violates unique constraint")) {
            return "An entry with the same key already exists in the database.";
        }
        return dbErrorMessage; // 기본 메시지 반환
    }

}

