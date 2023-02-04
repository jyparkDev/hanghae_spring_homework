package com.sparta.springbasic.service;

import com.sparta.springbasic.dto.BoardRequestDto;
import com.sparta.springbasic.entity.Board;
import com.sparta.springbasic.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;


    /**
     * 게시글 등록 기능
     * @param requestDto
     * @return Board 객체
     */
    @Transactional
    public Board createBoard(BoardRequestDto requestDto) {
        Board board = new Board(requestDto);
        boardRepository.save(board);
        return board;
    }
}
