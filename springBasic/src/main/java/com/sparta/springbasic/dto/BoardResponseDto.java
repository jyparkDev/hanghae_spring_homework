package com.sparta.springbasic.dto;

import com.sparta.springbasic.entity.Board;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
public class BoardResponseDto {
    private String title;
    private String writer;
    private String content;
    private LocalDateTime modifiedAt;
    private LocalDateTime createdAt;

    public BoardResponseDto(Board board){
        this.title = board.getTitle();
        this.writer = board.getWriter();
        this.content = board.getContent();
        this.modifiedAt = board.getModifiedAt();
        this.createdAt = board.getCreatedAt();
    }
}
