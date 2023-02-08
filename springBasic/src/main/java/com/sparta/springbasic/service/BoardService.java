package com.sparta.springbasic.service;

import com.sparta.springbasic.dto.BoardRequestDto;
import com.sparta.springbasic.dto.BoardResponseDto;
import com.sparta.springbasic.entity.Board;
import com.sparta.springbasic.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardService {
    private final BoardRepository boardRepository;

    /**
     * 게시글 등록 기능
     * @param requestDto
     * @return 등록된 게시물
     */

    public BoardResponseDto createBoard(BoardRequestDto requestDto) {
        Board board = Board.builder()
                .title(requestDto.getTitle())
                .writer(requestDto.getWriter())
                .passwd(requestDto.getPasswd())
                .content(requestDto.getContent())
                .build();
        boardRepository.save(board);
        return new BoardResponseDto(board);
    }

    /**
     * 전체 게시글 조회 기능
     * @param requestDto
     * @return 전체 게시글
     */
    @Transactional(readOnly = true)
    public List<BoardResponseDto> findBoards() {
        List<Board> boardList = boardRepository.findAllByOrderByCreatedAtDesc();
        List<BoardResponseDto> responseDtos = new ArrayList<>();
        for(Board board : boardList){
            responseDtos.add(new BoardResponseDto(board));
        }
        return responseDtos;
    }

    /**
     * 선택 게시글 조회 기능
     * @param id
     * @return
     */
    @Transactional(readOnly = true)
    public Board findBoard(Long id) {
        return boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 번호 게시글이 없습니다.")
        );
    }

    /**
     * 선택 게시글 수정 기능
     * @param id
     * @param requestDto
     * @return
     */
    public Board updateBoard(Long id,BoardRequestDto requestDto){
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 없습니다.")
        );
        if (!board.getPasswd().equals(requestDto.getPasswd())){
            throw new IllegalArgumentException("패스워드가 일치하지 않습니다.");
        }
        board.updateBoard(requestDto);
        return board;
    }

    public void removeBoard(Long id,String passwd) throws IllegalArgumentException, IllegalAccessException {
        Board board = boardRepository.findById(id).orElseThrow(
                ()->new IllegalArgumentException("해당 게시물이 없습니다.")
        );
        if(!board.getPasswd().equals(passwd)){
            throw new IllegalAccessException("비밀번호가 일치하지 않습니다");
        }
        boardRepository.delete(board);
    }
}



