package com.sparta.springbasic.service;

import com.sparta.springbasic.dto.CommentRequestDto;
import com.sparta.springbasic.dto.CommentResponseDto;
import com.sparta.springbasic.dto.StatusResponseDto;
import com.sparta.springbasic.entity.Board;
import com.sparta.springbasic.entity.Comment;
import com.sparta.springbasic.entity.User;
import com.sparta.springbasic.jwt.JwtUtil;
import com.sparta.springbasic.repository.BoardRepository;
import com.sparta.springbasic.repository.CommentRepository;
import com.sparta.springbasic.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final JwtUtil jwtUtil;


    public ResponseEntity createComment(Long id, CommentRequestDto requestDto, HttpServletRequest req){
        String token = jwtUtil.resolveToken(req);
        Claims claims;

        if(token != null){
            if (jwtUtil.validateToken(token)){
                claims = jwtUtil.getUserInfoFromToken(token);
            }else {
                return exceptionResponse("유효하지 않은 토큰입니다.");
            }
            Optional<User> findUser = userRepository.findByUsername(claims.getSubject());
            if(findUser.isEmpty()){
                return exceptionResponse("해당 유저는 없습니다.");
            }
            Optional<Board> findBoard = boardRepository.findByIdAndUserId(id, findUser.get().getId());
            if(findBoard.isEmpty()){
                return exceptionResponse("해당 게시글은 없습니다.");
            }
            Comment comment = Comment.builder()
                    .board(findBoard.get())
                    .user(findUser.get())
                    .content(requestDto.getContent())
                    .build();
            commentRepository.save(comment);
            return ResponseEntity.ok(new CommentResponseDto(comment,findUser.get(),findBoard.get()));

        }else {
            return exceptionResponse("토큰이 없습니다.");
        }
    }

    private ResponseEntity<StatusResponseDto> exceptionResponse(String msg){
        return ResponseEntity.badRequest()
                .body(new StatusResponseDto(HttpStatus.NO_CONTENT.value(), msg));
    }


}
