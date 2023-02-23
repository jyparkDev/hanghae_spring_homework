package com.sparta.boardbasic.controller;

import com.sparta.boardbasic.dto.requestDto.CommentRequestDto;
import com.sparta.boardbasic.dto.responseDto.CommentResponseDto;
import com.sparta.boardbasic.dto.responseDto.StatusMsgResponseDto;
import com.sparta.boardbasic.exception.CustomException;
import com.sparta.boardbasic.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController {
    private final CommentService commentService;

    /**
     * 게시글 댓글 작성 Controller
     */
    @PostMapping("/comment/{id}")
    public ResponseEntity<CommentResponseDto> createComment(@PathVariable Long id, @RequestBody CommentRequestDto commentRequestDto, HttpServletRequest req){
        return commentService.createComment(id,commentRequestDto,req);
    }

    /**
     * 게시글 댓글 수정 Controller
     */
    @PutMapping("/comment/{id}")
    public ResponseEntity<CommentResponseDto> updateComment(@PathVariable Long id, @RequestBody CommentRequestDto commentRequestDto, HttpServletRequest req){
        return commentService.updateComment(id, commentRequestDto.getContent(), req);
    }

    /**
     * 게시글 댓글 삭제 Controoler
     */
    @DeleteMapping("/comment/{id}")
    public ResponseEntity<StatusMsgResponseDto> deleteComment(@PathVariable Long id, HttpServletRequest req){
        return commentService.deleteComment(id,req);
    }
}
