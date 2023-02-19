
package com.sparta.springbasic.controller;

import com.sparta.springbasic.dto.BoardRequestDto;
import com.sparta.springbasic.dto.BoardResponseDto;
import com.sparta.springbasic.dto.StatusResponseDto;
import com.sparta.springbasic.security.UserDetailsImpl;
import com.sparta.springbasic.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
    public ResponseEntity ctreateBoard(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody BoardRequestDto requestDto) {
        return boardService.createBoard(userDetails.getUser(), requestDto.getTitle(), requestDto.getContent());
    }

    /**
     * 선택 게시글 조회 Controller
     */
    @GetMapping("/board/{id}")
    public ResponseEntity<BoardResponseDto> getBoard(@PathVariable Long id) {
        return boardService.findBoard(id);
    }

    /**
     * 선택 기능 수정 Controller
     */
    @PutMapping("/board/{id}")
    public ResponseEntity<Object> updateBoard(@PathVariable Long id, @RequestBody BoardRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return boardService.updateBoard(id, requestDto, userDetails);
    }

    /**
     * 선택 게시글 삭제 Controller
     */
    @DeleteMapping("/board/{id}")
    public ResponseEntity<StatusResponseDto> deleteBoard(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return boardService.removeBoard(id, userDetails);
    }

}

