package com.sparta.springbasic.dto;

import com.sparta.springbasic.entity.Board;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
public class BoardResponseDto {
    private String title;
    private String writer;
    private String content;

    public BoardResponseDto(Board board){
        this.title = board.getTitle();
        this.writer = board.getWriter();
        this.content = board.getContent();
    }
}
