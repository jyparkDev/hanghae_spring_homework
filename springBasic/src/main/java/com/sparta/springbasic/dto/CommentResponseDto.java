package com.sparta.springbasic.dto;

import com.sparta.springbasic.entity.Board;
import com.sparta.springbasic.entity.Comment;
import com.sparta.springbasic.entity.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {

    private Long id;
    private String content;
    private LocalDateTime createAt;
    private LocalDateTime modifiedAt;
    private String username;

    public CommentResponseDto(Comment comment, User user, Board board) {
        this.id = board.getId();
        this.content = comment.getContent();
        this.createAt = comment.getCreatedAt();
        this.modifiedAt = comment.getModifiedAt();
        this.username = user.getUsername();
    }
}
