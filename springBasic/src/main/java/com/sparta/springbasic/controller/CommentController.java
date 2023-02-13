package com.sparta.springbasic.controller;

import com.sparta.springbasic.dto.CommentRequestDto;
import com.sparta.springbasic.dto.StatusResponseDto;
import com.sparta.springbasic.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comment/{id}")
    public ResponseEntity<StatusResponseDto> createComment(@PathVariable Long id, @RequestBody CommentRequestDto requestDto, HttpServletRequest req){
        return commentService.createComment(id,requestDto,req);
    }

}
