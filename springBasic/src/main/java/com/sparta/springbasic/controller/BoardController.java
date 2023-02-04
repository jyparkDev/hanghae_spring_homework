package com.sparta.springbasic.controller;

import com.sparta.springbasic.dto.BoardRequestDto;
import com.sparta.springbasic.dto.BoardResponseDto;
import com.sparta.springbasic.entity.Board;
import com.sparta.springbasic.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    /**
     * 전체 게시글 조회  Controller
     * @return 전체 게시글
     */
    @GetMapping("/api/boards")
    public List<BoardResponseDto> getBoards() {
        return boardService.findBoards();
    }
    /**
     * 게시글 등록
     * @param requestDto
     * @return 등록 된 게시글
     */
    @PostMapping("/api/boards")
    public BoardResponseDto ctreateBoard(@RequestBody BoardRequestDto requestDto){
        Board board = boardService.createBoard(requestDto);
        return new BoardResponseDto(board);
    }
}


