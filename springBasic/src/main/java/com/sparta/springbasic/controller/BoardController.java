package com.sparta.springbasic.controller;

import com.sparta.springbasic.dto.BoardRequestDto;
import com.sparta.springbasic.dto.BoardResponseDto;
import com.sparta.springbasic.dto.StatusResponseDto;
import com.sparta.springbasic.entity.Board;
import com.sparta.springbasic.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    /**
     * 전체 게시글 조회  Controller
     */
    @GetMapping("/api/boards")
    public List<BoardResponseDto> getBoards() {
        return boardService.findBoards();
    }
    /**
     * 게시글 등록 Controller
     */
    @PostMapping("/api/boards")
    public BoardResponseDto ctreateBoard(@RequestBody BoardRequestDto requestDto){
        return boardService.createBoard(requestDto);
    }

    /**
     * 선택 게시글 조회 Controller
     */
    @GetMapping("/api/board/{id}")
    public BoardResponseDto getBoard(@PathVariable Long id){
        return new BoardResponseDto(boardService.findBoard(id));
    }

    /**
     * 선택 기능 수정 Controller
     */
    @PutMapping("/api/board/{id}")
    public BoardResponseDto updateBoard(@PathVariable Long id, @RequestBody BoardRequestDto requestDto){
        Board board = boardService.updateBoard(id, requestDto);
        return new BoardResponseDto(board);
    }

    /**
     * 선택 게시글 삭제 Controller
     */
    @DeleteMapping("/api/board/{id}")
    public StatusResponseDto deleteBoard(@PathVariable Long id, @RequestBody BoardRequestDto requestDto, HttpServletResponse res){
        return boardService.removeBoard(id,requestDto.getPasswd(),res);
    }
}


