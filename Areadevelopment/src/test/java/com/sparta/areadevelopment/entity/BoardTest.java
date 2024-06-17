package com.sparta.areadevelopment.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.sparta.areadevelopment.dto.BoardRequestDto;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class BoardTest {

    private Board board;
    private User user;
    private BoardRequestDto requestDto;

    @BeforeEach
    @DisplayName("Board 생성")
    void setUp() {
        user = new User(
                "User",
                "Nickname",
                "Asdfqwer1234",
                "User@email.com",
                "User info"
        );

        requestDto = new BoardRequestDto();
        requestDto.setTitle("테스트 제목");
        requestDto.setContent("테스트 내용");

        board = new Board(user, requestDto);
    }

    @Test
    @DisplayName("Board 생성 테스트")
    void test1() {
        // Given - When => setUp()

        //Then
        assertEquals(board.getTitle(), requestDto.getTitle());
        assertEquals(board.getContent(), requestDto.getContent());
        assertEquals(board.getHits(), 0L);
        assertEquals(board.getLikeCount(), 0L);
        assertEquals(board.getLikeCount(), 0L);
        assertThat(board.getModifiedAt()).isNotNull();
        assertThat(board.getDeletedAt()).isNull();
        assertThat(board.getUser()).isEqualTo(user);

    }

    @Test
    @DisplayName("update 테스트")
    void test2() throws InterruptedException {

        // Given
        BoardRequestDto updateRequestDto = new BoardRequestDto();
        updateRequestDto.setTitle("Update Title");
        updateRequestDto.setContent("Update content");
        LocalDateTime beforeUpdateTime = LocalDateTime.now();

        // 시간 비교 위해 1초 지연
        Thread.sleep(1000);

        // When
        board.update(updateRequestDto);

        // Then
        assertThat(board.getTitle()).isEqualTo(updateRequestDto.getTitle());
        assertThat(board.getContent()).isEqualTo(updateRequestDto.getContent());
        assertThat(board.getModifiedAt()).isNotEqualTo(beforeUpdateTime);
    }

    @Test
    @DisplayName("hitsUp 테스트")
    void test3() {
        board.hitsUp();
        board.hitsUp();
        board.hitsUp();
        board.hitsUp();
        board.hitsUp();

        assertThat(board.getHits()).isEqualTo(5);
    }

}
