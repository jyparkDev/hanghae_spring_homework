package com.sparta.boardbasic.service;

import com.sparta.boardbasic.dto.requestDto.BoardRequestDto;
import com.sparta.boardbasic.dto.responseDto.BoardResponseDto;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 게시글 관련 Service
 */
@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final JwtUtil jwtUtil;


    /**
     * 게시글 등록 기능
     */
    @Transactional
    public ResponseEntity<BoardResponseDto> createBoard(BoardRequestDto requestDto, HttpServletRequest req) {
        User findUser = isValidTokenAndUser(req);

        // 해당 회원으로 게시글 만들기
        Board board = Board.builder()
                .requestDto(requestDto)
                .user(findUser)
                .build();

        // DB에 작성한 게시글 등록
        boardRepository.save(board);
        return ResponseEntity.ok(new BoardResponseDto(board));
    }


    /**
     * 게시글 전체 조회
    */
    @Transactional(readOnly = true)
    public ResponseEntity<List<BoardResponseDto>> getBoards() {
        // 수정시간 내림차순으로 모든 게시글 조회
        List<Board> boardList = boardRepository.findAllByOrderByModifiedAtDesc();
        // 응답으로 보내줄 게시물 List
        List<BoardResponseDto> responseBoardList = new ArrayList<>();

        /* 댓글 여러개 일 수 있기에 List에 담는다
         댓글로 응답하기 위한 Dto를 활용*/
        List<CommentResponseDto> commentList = new ArrayList<>();

        // 각각의 게시글에 달린 댓글들을 등록일 기준 내림차순으로 정렬하여 해당 게시글에 담는 작업
        for (Board board : boardList) {
            //Board -> BoardResponseDto로 변환
            commentList = commentRepository.findAllByBoardOrderByCreatedAtDesc(board)
                    .stream()
                    .map(CommentResponseDto::new)
                    .collect(Collectors.toList());
            responseBoardList.add(new BoardResponseDto(board, commentList));
        }
        return ResponseEntity.ok(responseBoardList);
    }

    /**
     * 선택한 게시글 조회
     */
    @Transactional(readOnly = true)
    public ResponseEntity<BoardResponseDto> getBoard(Long id) {
        //게시글 id로 해당 게시글 찾기
        Optional<Board> findBoard = boardRepository.findById(id);
        if (findBoard.isEmpty()) {
            throw new CustomException(ErrorCodeEnum.NOT_EXIST_RESOURCE);
        }
        // 해당 게시글에 달린 댓글들을 등록일 기준 내림차순으로 정렬하여 해당 게시글에 담는 작업
        List<CommentResponseDto> commentList = commentRepository.findAllByBoardOrderByCreatedAtDesc(findBoard.get())
                .stream()
                .map(CommentResponseDto::new)
                .collect(Collectors.toList());
        // 게시글 내용을 응답으로 보내줄 boardResponseDto를 생성
        BoardResponseDto boardResponseDto = new BoardResponseDto(findBoard.get(), commentList);

        //찾은 게시글 BoardResponseDto에 넣어서 반환
        return ResponseEntity.ok()
                .body(boardResponseDto);
    }

    /**
     * 게시글 수정 기능 구현
     */
    @Transactional
    public ResponseEntity<BoardResponseDto> updateBoard(Long id, BoardRequestDto requestDto, HttpServletRequest req) {
        User findUser = isValidTokenAndUser(req);

        // 요청으로 받은 게시글 Id, 검색으로 찾은 user를 통하여 게시글 있는지 조회
        Optional<Board> findBoard = boardRepository.findById(id);
        if (findBoard.isEmpty()) {
            throw new CustomException(ErrorCodeEnum.NOT_EXIST_RESOURCE);
        }
        //권한이 USER일 떄 내가 작성한 글인지 확인
        if (findUser.getRole() == UserRoleEnum.USER && findBoard.get().getUser() != findUser) {
            throw new CustomException(ErrorCodeEnum.INVALID_AUTH_MEMBER);
        }

        findBoard.get().update(requestDto);
        return ResponseEntity.ok(new BoardResponseDto(findBoard.get()));
    }


    /**
     * 게시글 삭제 기능 구현
     */
    @Transactional
    public ResponseEntity<StatusMsgResponseDto> deleteBoard(Long id, HttpServletRequest req) {
        User findUser = isValidTokenAndUser(req);
        // 삭제하고 싶은 게시글 번호로 DB에서 게시글 찾아오기
        Optional<Board> findBoard = boardRepository.findById(id); 
        if (findBoard.isEmpty()) {
            throw new CustomException(ErrorCodeEnum.NOT_EXIST_RESOURCE);
        }

        //권한이 USER일 떄 내가 작성한 글인지 확인
        if (findUser.getRole() == UserRoleEnum.USER && findBoard.get().getUser() != findUser) {
            throw new CustomException(ErrorCodeEnum.INVALID_AUTH_MEMBER);
        }

        //해당 게시글 관련 댓글 모두 삭제
        commentRepository.deleteAllByBoard(findBoard.get());

        // 해당 게시글 삭제
        boardRepository.delete(findBoard.get());

        return ResponseEntity.ok()
                .body(new StatusMsgResponseDto("삭제완료", HttpStatus.OK.value()));

    }

    /**
     * 토큰 유효성과 회원유무 확인 메서드
     -> 등록, 수정, 삭제 시 토큰의 유효성 + 사용자 확인하는 코드의 반복을
        제거하기 위해 하나의 메서드로 분리하여 처리함
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
        User findUser = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                () -> new CustomException(ErrorCodeEnum.MEMBER_NOT_FOUND)
        );
        return findUser;
    }

}
