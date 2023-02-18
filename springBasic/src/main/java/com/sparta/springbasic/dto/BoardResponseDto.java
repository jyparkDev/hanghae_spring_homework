package com.sparta.springbasic.dto;

import com.sparta.springbasic.entity.Board;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BoardResponseDto {
    private Long id;
    private String title;
    private String userName;
    private String content;
    private LocalDateTime modifiedAt;
    private LocalDateTime createdAt;


    @Builder
    public BoardResponseDto(Board board){
        this.id = board.getId();
        this.title = board.getTitle();
        this.userName = board.getUser().getUsername();
        this.content = board.getContent();
        this.modifiedAt = board.getModifiedAt();
        this.createdAt = board.getCreatedAt();
    }
}
