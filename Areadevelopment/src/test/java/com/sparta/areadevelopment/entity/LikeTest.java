package com.sparta.areadevelopment.entity;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.sparta.areadevelopment.enums.LikeTypeEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

class LikeTest {

    @Mock
    private User user;

    private Like like;
    private Long contentId;
    private LikeTypeEnum contentType;

    @BeforeEach
    void setUp(){
        user = new User(
                "User",
                "Nickname",
                "Asdfqwer1234",
                "User@email.com",
                "User info"
        );
    }

    @Test
    @DisplayName("Like 생성 (board)")
    void test1() {
        // Given
        contentId = 1L;
        contentType = LikeTypeEnum.BOARD;

        // When
        like = new Like(user, contentId, contentType);

        // Then
        assertThat(like.getUser()).isEqualTo(user);
        assertThat(like.getContentId()).isEqualTo(contentId);
        assertThat(like.getContentType()).isEqualTo(contentType);
        assertThat(like.getCreatedAt()).isNotNull();
    }

    @Test
    @DisplayName("Like 생성 (comment)")
    void test2() {
        // Given
        contentId = 2L;
        contentType = LikeTypeEnum.COMMENT;

        // When
        like = new Like(user, contentId, contentType);

        // Then
        assertThat(like.getUser()).isEqualTo(user);
        assertThat(like.getContentId()).isEqualTo(contentId);
        assertThat(like.getContentType()).isEqualTo(contentType);
        assertThat(like.getCreatedAt()).isNotNull();
    }

}