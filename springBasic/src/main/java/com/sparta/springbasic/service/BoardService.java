package com.sparta.springbasic.service;

import com.sparta.springbasic.dto.BoardRequestDto;
import com.sparta.springbasic.dto.BoardResponseDto;
import com.sparta.springbasic.dto.StatusResponseDto;
import com.sparta.springbasic.entity.Board;
import com.sparta.springbasic.entity.User;
import com.sparta.springbasic.jwt.JwtUtil;
import com.sparta.springbasic.repository.BoardRepository;
import com.sparta.springbasic.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    public ResponseEntity<Object> createBoard(BoardRequestDto requestDto, HttpServletRequest req) {
        // 헤더에서 토큰 가져오기
        String token = jwtUtil.resolveToken(req);
        Claims claims;

        if(token != null){

            if(jwtUtil.validateToken(token)){ // 토큰 유효 체크
                claims = jwtUtil.getUserInfoFromToken(token);
            }else {
                return exceptionResponse("토큰이 유효하지 않습니다.");
            }
            Optional<User> findUser = userRepository.findByUsername(claims.getSubject());
            if(findUser.isEmpty()){
                return exceptionResponse("유효하지 않은 계정입니다.");
            }
            Board board = Board.builder().user(findUser.get()).requestDto(requestDto).build();
            boardRepository.save(board);
            return ResponseEntity.ok(new BoardResponseDto(board));
        }else {
            return exceptionResponse("토큰이 없습니다.");
        }
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
    public ResponseEntity<Object> updateBoard(Long id,BoardRequestDto requestDto,HttpServletRequest req){
        String token = jwtUtil.resolveToken(req);
        Claims claims;

        if(token != null){
            if(jwtUtil.validateToken(token)){
                claims = jwtUtil.getUserInfoFromToken(token);
            }else{
                return exceptionResponse("토큰이 유효하지 않습니다.");
            }
            Optional<User> findUser = userRepository.findByUsername(claims.getSubject());
            if(findUser.isEmpty()){
                return exceptionResponse("유효하지 않은 계정입니다.");
            }
            Optional<Board> board = boardRepository.findByIdAndUserId(id,findUser.get().getId());
            if (board.isEmpty()){
                return exceptionResponse("해당 번호 게시글이 없습니다.");
            }
            board.get().updateBoard(requestDto); //자동감지로 업데이트
            return ResponseEntity.ok(new BoardResponseDto(board.get()));
        }else{
            return exceptionResponse("토큰이 없습니다.");
        }

    }

    /**
     * 선택 게시글 삭제
     */
    public ResponseEntity<StatusResponseDto> removeBoard(Long id, HttpServletRequest req)  {
        String token = jwtUtil.resolveToken(req);
        Claims claims;

        if(token != null){
            if(jwtUtil.validateToken(token)){
                claims = jwtUtil.getUserInfoFromToken(token);
            }else{
                return exceptionResponse("토큰이 유효하지 않습니다.");
            }
            Optional<User> findUser = userRepository.findByUsername(claims.getSubject());
            if(findUser.isEmpty()){
                return exceptionResponse("해당 유저가 없습니다.");
            }
            Optional<Board> findBoard = boardRepository.findByIdAndUserId(id, findUser.get().getId());
            if (findBoard.isEmpty()){
                return exceptionResponse("유효하지 않은 게시물입니다.");
            }
            boardRepository.delete(findBoard.get());
            return ResponseEntity.ok(new StatusResponseDto(HttpStatus.OK.value(), "삭제 완료"));
        }else {
            return exceptionResponse("토큰이 없습니다.");
        }
    }

    private ResponseEntity exceptionResponse(String msg) {
        return ResponseEntity.badRequest()
                .body(new StatusResponseDto(HttpStatus.BAD_REQUEST.value(), msg));
    }
}



