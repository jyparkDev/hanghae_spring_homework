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
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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


//
//    /**
//     * 전체 게시글 조회 기능
//     */
//    @Transactional(readOnly = true)
//    public ResponseEntity<List<BoardResponseDto>> findBoards() {
//        List<Board> boardList = boardRepository.findAllByOrderByCreatedAtDesc();
//        List<BoardResponseDto> responseDtos = new ArrayList<>();
//        for(Board board : boardList){
//            responseDtos.add(new BoardResponseDto(board));
//        }
//        return ResponseEntity.ok(responseDtos);
//    }

//    /**
//     * 선택 게시글 조회 기능
//     */
//    @Transactional(readOnly = true)
//    public ResponseEntity findBoard(Long id) {
//        Optional<Board> findBoard = boardRepository.findById(id);
//        if(findBoard.isEmpty()){
//            return ResponseEntity.badRequest().
//        }
//
//        return ResponseEntity.ok(new BoardResponseDto(findBoard));
//    }

    /**
     * 선택 게시글 수정 기능
     */
//    public Board updateBoard(Long id,BoardRequestDto requestDto){
//        Board board = boardRepository.findById(id).orElseThrow(
//                () -> new IllegalArgumentException("해당 게시글이 없습니다.")
//        );
//        if (!board.getPasswd().equals(requestDto.getPasswd())){
//            throw new IllegalArgumentException("패스워드가 일치하지 않습니다.");
//        }
//        Board newBoard = new Board();
//        board.updateBoard(requestDto); //자동감지로 업데이트
//        return board;
//    }

    /**
     * 선택 게시글 삭제
     *//*
    public StatusResponseDto removeBoard(Long id, String passwd, HttpServletResponse res)  {
        try{
            Board board = boardRepository.findById(id).orElseThrow(
                    () -> new IllegalArgumentException("해당 게시물이 없습니다.")
            );
            if(!board.getPasswd().equals(passwd)){
                throw new IllegalAccessException("비밀번호가 일치하지 않습니다");
            }
            res.setStatus(HttpServletResponse.SC_OK);
            boardRepository.delete(board);
            return new StatusResponseDto("200","삭제 성고!");
        }catch (IllegalAccessException e){
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return new StatusResponseDto("400",e.getMessage());
        }catch (IllegalArgumentException e){
            res.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return new StatusResponseDto("404",e.getMessage());
        }
    }*/

    private ResponseEntity exceptionResponse(String msg) {
        return ResponseEntity.badRequest()
                .body(new StatusResponseDto(HttpStatus.BAD_REQUEST.value(), msg));
    }
}



