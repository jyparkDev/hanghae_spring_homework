package com.sparta.springbasic.dto;

import lombok.Getter;

@Getter
public class BoardRequestDto {
    private String title;
    private String writer;
    private String passwd;
    private String content;
}
