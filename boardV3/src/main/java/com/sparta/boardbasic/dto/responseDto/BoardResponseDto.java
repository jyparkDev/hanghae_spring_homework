package com.sparta.boardbasic.dto.responseDto;

import com.sparta.boardbasic.entity.Board;
import com.sparta.boardbasic.entity.Comment;
import lombok.Builder;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

//게시글 관련 응답 할 DTO(=ResponseDto)
@Getter
public class BoardResponseDto {
    private Long id; // 게시글 번호
    private String title; // 게시글 제목
    private String author; // 게시글 작성자
    private String content; // 게시글 내용
    private LocalDateTime createdAt; // 작성일자
    private LocalDateTime modifiedAt; // 수정일자
    private List<CommentResponseDto> comments = new ArrayList<>();

    public BoardResponseDto(Board board) {
        this(board, new ArrayList<>());
    }

    public BoardResponseDto(Board board,List<CommentResponseDto> comments) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.author = board.getUser().getUsername();
        this.content = board.getContent();
        this.createdAt = board.getCreatedAt();
        this.modifiedAt = board.getModifiedAt();
        this.comments = comments;
    }
}
