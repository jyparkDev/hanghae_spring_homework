
package com.sparta.springbasic.controller;

import com.sparta.springbasic.dto.BoardRequestDto;
import com.sparta.springbasic.dto.BoardResponseDto;


import com.sparta.springbasic.service.BoardService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RestController
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;


/**
 * 전체 게시글 조회  Controller
 */

    @GetMapping("/api/boards")
    public ResponseEntity<List<BoardResponseDto>> getBoards() {
        return boardService.findBoards();
    }

    /**
     * 게시글 등록 Controller
     */
    @PostMapping("/api/boards")
    public ResponseEntity ctreateBoard(@RequestBody BoardRequestDto requestDto, HttpServletRequest req) {
        return boardService.createBoard(requestDto, req);
    }

/**
 * 선택 게시글 조회 Controller
 */

    @GetMapping("/api/board/{id}")
    public ResponseEntity<BoardResponseDto> getBoard(@PathVariable Long id){
        return boardService.findBoard(id);
    }


/**
 * 선택 기능 수정 Controller
 *//*
     */
/*
    @PutMapping("/api/board/{id}")
    public BoardResponseDto updateBoard(@PathVariable Long id, @RequestBody BoardRequestDto requestDto){
        Board board = boardService.updateBoard(id, requestDto);
        return new BoardResponseDto(board);
    }

    *//*
     */
/**
 * 선택 게시글 삭제 Controller
 *//*
     */
/*
    @DeleteMapping("/api/board/{id}")
    public StatusResponseDto deleteBoard(@PathVariable Long id, @RequestBody BoardRequestDto requestDto, HttpServletResponse res){
        return boardService.removeBoard(id,requestDto.getPasswd(),res);
    }*//*

}


*/
}