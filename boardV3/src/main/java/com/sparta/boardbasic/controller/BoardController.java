package com.sparta.boardbasic.controller;

import com.sparta.boardbasic.dto.requestDto.BoardRequestDto;
import com.sparta.boardbasic.dto.responseDto.BoardResponseDto;
import com.sparta.boardbasic.dto.responseDto.StatusMsgResponseDto;
import com.sparta.boardbasic.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 게시글 관련 Controller
 */
@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    /**
     * 게시글 저장 Controller
     */
    @PostMapping("/api/boards") //URL 경로 매핑
    public ResponseEntity<BoardResponseDto> createBoard(@RequestBody BoardRequestDto requestDto, HttpServletRequest req){
        return boardService.createBoard(requestDto,req);
    }

    /**
     * 게시글 전체 조회 Controller
     */
    @GetMapping("/api/boards")
    public ResponseEntity<List<BoardResponseDto>> getBoards(){
        return boardService.getBoards();
    }

    /**
     * 해당 게시글 조회 Controller
     */
    @GetMapping("/api/board/{id}")
    public ResponseEntity<BoardResponseDto> getBoard(@PathVariable Long id){
        return boardService.getBoard(id);
    }

    /**
     * 해당 게시글 수정 Controller
     */
    @PutMapping("/api/board/{id}")
    public ResponseEntity<BoardResponseDto> updateBoard(@PathVariable Long id, @RequestBody BoardRequestDto requestDto, HttpServletRequest req){
        return boardService.updateBoard(id,requestDto,req);
    }

    /**
     * 해당 게시글 삭제 Controller
     */
    @DeleteMapping("/api/board/{id}")
    public ResponseEntity<StatusMsgResponseDto> deleteBoard(@PathVariable Long id, HttpServletRequest req, HttpServletResponse res){
        return boardService.deleteBoard(id, req);
    }
}
