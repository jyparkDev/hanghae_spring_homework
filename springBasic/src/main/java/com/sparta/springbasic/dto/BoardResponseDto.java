package com.sparta.springbasic.dto;

import com.sparta.springbasic.entity.Board;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
public class BoardResponseDto {
    private Long id;
    private String title;
    private String writer;
    private String content;
    private LocalDateTime modifiedAt;
    private LocalDateTime createdAt;


    public BoardResponseDto(Board board){
        this.id = board.getId();
        this.title = board.getTitle();
        this.writer = board.getWriter();
        this.content = board.getContent();
        this.modifiedAt = board.getModifiedAt();
        this.createdAt = board.getCreatedAt();
    }
}
