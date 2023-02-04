package com.sparta.springbasic.controller;

import com.sparta.springbasic.dto.BoardRequestDto;
import com.sparta.springbasic.dto.BoardResponseDto;
import com.sparta.springbasic.entity.Board;
import com.sparta.springbasic.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
     * 게시글 등록 Controller
     * @param requestDto
     * @return 등록 된 게시글
     */
    @PostMapping("/api/boards")
    public BoardResponseDto ctreateBoard(@RequestBody BoardRequestDto requestDto){
        return new BoardResponseDto(boardService.createBoard(requestDto));
    }

    /**
     * 선택 게시글 조회 Controller
     * @param id
     * @return
     */
    @GetMapping("/api/board/{id}")
    public BoardResponseDto getBoard(@PathVariable Long id){
        return new BoardResponseDto(boardService.findBoard(id));
    }

    /**
     * 선택 기능 수정 Controller
     * @param id
     * @param requestDto
     * @return
     */
    @PutMapping("/api/board/{id}")
    public BoardResponseDto updateBoard(@PathVariable Long id, @RequestBody BoardRequestDto requestDto){
        Board board = boardService.updateBoard(id, requestDto);
        return new BoardResponseDto(board);
    }

    @DeleteMapping("/api/board/{id}")
    public Map<String,String> deleteBoard(@PathVariable Long id, @RequestBody BoardRequestDto requestDto){
        Map<String,String> result = new HashMap<>();
        try{
            boardService.removeBoard(id,requestDto.getPasswd());
            result.put("msg","2xx");
        }catch (IllegalArgumentException e){
            result.put("msg","4x4");
        }catch (IllegalAccessException e){
            result.put("msg","4x3");
        }
        return result;
    }
}


