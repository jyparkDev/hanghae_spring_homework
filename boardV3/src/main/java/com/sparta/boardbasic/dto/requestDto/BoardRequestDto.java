package com.sparta.boardbasic.dto.requestDto;

import lombok.Getter;

/**
 * 게시글 관련하여 요청받은 RequestDto
 */
@Getter
public class BoardRequestDto {
    private String title; // 게시글 제목
    private String content; // 게시글 내용
}
