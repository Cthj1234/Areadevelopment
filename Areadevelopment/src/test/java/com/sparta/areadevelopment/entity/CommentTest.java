package com.sparta.areadevelopment.entity;

import static org.assertj.core.api.Assertions.assertThat;

import com.sparta.areadevelopment.dto.BoardRequestDto;
import com.sparta.areadevelopment.dto.CommentRequestDto;
import java.lang.reflect.Field;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


public class CommentTest {

    private User user;
    private Board board;
    private BoardRequestDto boardRequestDto;
    private CommentRequestDto commentRequestDto;
    private Comment comment;

    @BeforeEach
    @DisplayName("Comment 생성")
    void setUp() {

        user = new User(
                "User",
                "Nickname",
                "Asdfqwer1234",
                "User@email.com",
                "User info"
        );

        boardRequestDto = new BoardRequestDto();
        boardRequestDto.setTitle("테스트 제목");
        boardRequestDto.setContent("테스트 내용");

        board = new Board(
                user,
                boardRequestDto
        );

        commentRequestDto = new CommentRequestDto("Test Comment content");

        comment = new Comment(
                commentRequestDto.getContent(),
                board,
                user
        );
    }

    @Test
    @DisplayName("Comment 생성 테스트")
    void test1() {
        //Given - When => setUp()

        //Then
        assertThat(comment.getContent()).isEqualTo(commentRequestDto.getContent());
        assertThat(comment.getLikeCount()).isEqualTo(0L);
        assertThat(comment.getBoard()).isNotNull();
        assertThat(comment.getUser()).isNotNull();
    }

    @Test
    @DisplayName("Comment 수정 테스트")
    void test2() {
        //Given
        CommentRequestDto updateRequestDto = new CommentRequestDto("수정한 댓글 내용");

        //When
        comment.update(updateRequestDto);

        //Then
        assertThat(comment.getContent()).isEqualTo(updateRequestDto.getContent());

    }

    @Test
    @DisplayName("Comment delete 테스트")
    void test3(){

        assertThat(comment.getDeletedAt()).isNull();

        // When
        comment.delete();

        // Then
        assertThat(comment.getDeletedAt()).isNotNull();
    }

    @Test
    @DisplayName("댓글 작성자 확인 테스트")
    void test4() throws NoSuchFieldException, IllegalAccessException {

        Field userIdField = User.class.getDeclaredField("id");
        userIdField.setAccessible(true);
        userIdField.set(user, 10_000L);

        // Given
        Long userId = user.getId();
        Long userId2 = 12_312L;

        // When
        boolean checkTrue = comment.isCommentAuthor(userId);
        boolean checkFalse = comment.isCommentAuthor(userId2);

        // When
        assertThat(checkTrue).isTrue();
        assertThat(checkFalse).isFalse();

        userIdField.setAccessible(false);
    }

}
