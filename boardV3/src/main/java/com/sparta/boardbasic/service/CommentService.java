package com.sparta.boardbasic.service;

import com.sparta.boardbasic.dto.requestDto.CommentRequestDto;
import com.sparta.boardbasic.dto.responseDto.CommentResponseDto;
import com.sparta.boardbasic.dto.responseDto.StatusMsgResponseDto;
import com.sparta.boardbasic.entity.Board;
import com.sparta.boardbasic.entity.Comment;
import com.sparta.boardbasic.entity.User;
import com.sparta.boardbasic.entity.enumSet.ErrorCodeEnum;
import com.sparta.boardbasic.entity.enumSet.UserRoleEnum;
import com.sparta.boardbasic.exception.CustomException;
import com.sparta.boardbasic.jwt.JwtUtil;
import com.sparta.boardbasic.repository.BoardRepository;
import com.sparta.boardbasic.repository.CommentRepository;
import com.sparta.boardbasic.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final JwtUtil jwtUtil;

    /**
     * 댓글 작성 기능 구현
     */
    public ResponseEntity<CommentResponseDto> createComment(Long id, CommentRequestDto commentRequestDto, HttpServletRequest req) {
        User findUser = isValidTokenAndUser(req);

        Optional<Board> findBoard = boardRepository.findById(id);
        if (findBoard.isEmpty()) {
            throw new CustomException(ErrorCodeEnum.NOT_EXIST_RESOURCE);
        }
        // 댓글 생성하기
        Comment comment = Comment.builder()
                .user(findUser)
                .board(findBoard.get())
                .content(commentRequestDto.getContent())
                .build();

        // 댓글 DB에 저장하기
        commentRepository.save(comment);
        return ResponseEntity.ok(CommentResponseDto.builder()
                .comment(comment)
                .build());
    }

    /**
     * 댓글 수정 기능
     */
    public ResponseEntity<CommentResponseDto> updateComment(Long commentId, String commentCotent, HttpServletRequest req) {
        User findUser = isValidTokenAndUser(req);

        Optional<Comment> findComment = commentRepository.findById(commentId);
        if (findComment.isEmpty()) {
            throw new CustomException(ErrorCodeEnum.NOT_EXIST_RESOURCE);
        }

        //권한이 USER인데, 자신이 쓴 댓글이 아닐 경우
        if (findUser.getRole() == UserRoleEnum.USER && findComment.get().getUser() != findUser) {
            throw new CustomException(ErrorCodeEnum.INVALID_AUTH_MEMBER);
        }

        // 자동감지로 댓글 수정하기
        findComment.get().updateComment(commentCotent);
        return ResponseEntity.ok().body(
                CommentResponseDto.builder()
                        .comment(findComment.get())
                        .build()
        );
    }

    /**
     * 댓글 삭제 기능
     */
    public ResponseEntity<StatusMsgResponseDto> deleteComment(Long id, HttpServletRequest req) {
        User findUser = isValidTokenAndUser(req);

        // 댓글 번호로 해당 댓글정도 DB에서 가져오기
        Optional<Comment> findComment = commentRepository.findById(id);
        if (findComment.isEmpty()) {
            throw new CustomException(ErrorCodeEnum.NOT_EXIST_RESOURCE);
        }

        //권한이 USER인데, 자신이 쓴 댓글이 아닐 경우
        if (findUser.getRole() == UserRoleEnum.USER && findComment.get().getUser() != findUser) {
            throw new CustomException(ErrorCodeEnum.INVALID_AUTH_MEMBER);
        }

        // 해당 댓글 삭제
        commentRepository.delete(findComment.get());
        return ResponseEntity.ok(new StatusMsgResponseDto("댓글 삭제 완료!!", HttpStatus.OK.value()));
    }

    /**
     * 토큰 유효성 확인 및 회원 체크
     */
    private User isValidTokenAndUser(HttpServletRequest req) {
        // 요청 Header로 전달되는 토큰을 얻기
        String token = jwtUtil.resolveToken(req);
        Claims claims;
        // token이 있는지 없는지 확인
        // null이면 token이 없음
        if (token == null) {
            throw new CustomException(ErrorCodeEnum.FOUND_NOT_TOKEN);
        }

        if (jwtUtil.validateToken(token)) {
            claims = jwtUtil.getUserInfoFromToken(token); // 토큰에 있는 회원 정보 얻어오기
        } else {
            throw new CustomException(ErrorCodeEnum.INVALID_TOKEN);
        }
        // 토큰에 생성 시 사용한 회원명 얻어서 해당 회원이 있는지 DB에서 찾기
        Optional<User> findUser = userRepository.findByUsername(claims.getSubject());
        if (findUser.isEmpty()) {
            throw new CustomException(ErrorCodeEnum.MEMBER_NOT_FOUND);
        }
        return findUser.get();
    }
}
