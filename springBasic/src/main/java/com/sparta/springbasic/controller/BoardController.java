
package com.sparta.springbasic.controller;

import com.sparta.springbasic.dto.BoardRequestDto;
import com.sparta.springbasic.dto.BoardResponseDto;


import com.sparta.springbasic.dto.StatusResponseDto;
import com.sparta.springbasic.entity.Board;
import com.sparta.springbasic.service.BoardService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BoardController {
    private final BoardService boardService;


/**
 * 전체 게시글 조회  Controller
 */

    @GetMapping("/boards")
    public ResponseEntity<List<BoardResponseDto>> getBoards() {
        return boardService.findBoards();
    }

    /**
     * 게시글 등록 Controller
     */
    @PostMapping("/boards")
    public ResponseEntity ctreateBoard(@RequestBody BoardRequestDto requestDto, HttpServletRequest req) {
        return boardService.createBoard(requestDto, req);
    }

/**
 * 선택 게시글 조회 Controller
 */

    @GetMapping("/board/{id}")
    public ResponseEntity<BoardResponseDto> getBoard(@PathVariable Long id){
        return boardService.findBoard(id);
    }

/**
 * 선택 기능 수정 Controller
 */
    @PutMapping("/board/{id}")
    public ResponseEntity<Object> updateBoard(@PathVariable Long id, @RequestBody BoardRequestDto requestDto, HttpServletRequest req){
        return boardService.updateBoard(id, requestDto,req);
    }

/**
 * 선택 게시글 삭제 Controller
 */

    @DeleteMapping("/board/{id}")
    public ResponseEntity<StatusResponseDto> deleteBoard(@PathVariable Long id, HttpServletRequest req){
        return boardService.removeBoard(id,req);
    }

}

