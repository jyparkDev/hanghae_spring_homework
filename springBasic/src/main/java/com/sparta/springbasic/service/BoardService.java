package com.sparta.springbasic.service;

import com.sparta.springbasic.dto.BoardRequestDto;
import com.sparta.springbasic.dto.BoardResponseDto;
import com.sparta.springbasic.dto.StatusResponseDto;
import com.sparta.springbasic.entity.Board;
import com.sparta.springbasic.entity.User;
import com.sparta.springbasic.entity.UserRoleEnum;
import com.sparta.springbasic.jwt.JwtUtil;
import com.sparta.springbasic.repository.BoardRepository;
import com.sparta.springbasic.repository.UserRepository;
import com.sparta.springbasic.security.UserDetailsImpl;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    /**
     * 게시글 등록 기능
     */

    public ResponseEntity<Object> createBoard(User user, String title, String content) {

        Board board = Board.builder()
                .user(user)
                .title(title)
                .content(content)
                .build();
        boardRepository.save(board);
        return ResponseEntity.ok(new BoardResponseDto(board));

    }

    /**
     * 전체 게시글 조회 기능
     */
    @Transactional(readOnly = true)
    public ResponseEntity<List<BoardResponseDto>> findBoards() {
        return ResponseEntity.ok(boardRepository.findAllByOrderByModifiedAtDesc()
                .stream()
                .map(BoardResponseDto::new)
                .collect(Collectors.toList()));
    }

    /**
     * 선택 게시글 조회 기능
     */
    @Transactional(readOnly = true)
    public ResponseEntity<BoardResponseDto> findBoard(Long id) {
        Optional<Board> findBoard = boardRepository.findById(id); // 해당 번호 게시글 찾기
        if(findBoard.isEmpty()){ // 찾은 결과가 있는지 확인
            return exceptionResponse("해당 게시글이 없습니다.");
        }
        return ResponseEntity.ok(new BoardResponseDto(findBoard.get())); // 해당 게시글 반환
    }

    /**
     * 선택 게시글 수정 기능
     */
    public ResponseEntity<Object> updateBoard(Long id,BoardRequestDto requestDto,UserDetailsImpl userDetails){
        Board board ;
        try{
            board = isValidBoard(id, userDetails.getUser().getId(),userDetails.getUser().getRole())
                    .updateBoard(requestDto.getTitle(),requestDto.getContent());
        }catch (IllegalStateException e){
            return exceptionResponse("유효하지 않은 게시물입니다.");
        }
        return ResponseEntity.ok(new BoardResponseDto(board));
    }

    /**
     * 선택 게시글 삭제
     */
    public ResponseEntity<StatusResponseDto> removeBoard(Long id, UserDetailsImpl userDetails) {
        try{
            boardRepository.delete(isValidBoard(id, userDetails.getUser().getId(),userDetails.getUser().getRole()));
        }catch (IllegalStateException e){
            return exceptionResponse("유효하지 않은 게시물입니다.");
        }
        return ResponseEntity.ok(new StatusResponseDto(HttpStatus.OK.value(), "삭제 완료"));

    }

    private Board isValidBoard(Long id, Long userId,UserRoleEnum role) {
        Optional<Board> board = role == UserRoleEnum.ADMIN
                ? boardRepository.findById(id)
                : boardRepository.findByIdAndUserId(id, userId);
        if (board.isEmpty()) {
            throw new IllegalStateException();
        }
        return board.get();
    }

    private ResponseEntity exceptionResponse(String msg) {
        return ResponseEntity.badRequest()
                .body(new StatusResponseDto(HttpStatus.BAD_REQUEST.value(), msg));
    }
}



