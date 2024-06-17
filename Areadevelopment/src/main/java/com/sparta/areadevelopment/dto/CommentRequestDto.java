package com.sparta.areadevelopment.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentRequestDto {

    /**
     * 댓글의 내용입니다.
     */
    private String content;
}
