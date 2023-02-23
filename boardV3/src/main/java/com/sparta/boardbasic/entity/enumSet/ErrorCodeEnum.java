package com.sparta.boardbasic.entity.enumSet;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import static org.springframework.http.HttpStatus.*;


/**
 *  ["토큰이 유효하지 않습니다."] : 토큰이 필요한 API 요청에서 토큰을 전달하지 않았거나 정상 토큰이 아닐 시
 *  [“작성자만 삭제/수정할 수 있습니다.”] : 토큰이 있고, 유효한 토큰이지만 해당 사용자가 작성한 게시글/댓글이 아닐 시
 *  ["중복된 username 입니다."] : DB에 이미 존재하는 username으로 회원가입을 요청한 시
 *  ["회원을 찾을 수 없습니다.] : 로그인 시, 전달된 username과 password 중 맞지 않는 정보가 있을 시
 */

// 공통 예외처리 코드를 관리하기 위한 enum
@Getter
@AllArgsConstructor
public enum ErrorCodeEnum {

    FOUND_NOT_TOKEN(BAD_REQUEST, "토큰이 없습니다"),
    INVALID_TOKEN(BAD_REQUEST, "토큰이 유효하지 않습니다"),
    MEMBER_NOT_FOUND(BAD_REQUEST, "회원을 찾을 수 없습니다"),
    DUPLICATE_MEMBER(BAD_REQUEST,"중복된 회원입니다"),
    INVALID_AUTH_MEMBER(BAD_REQUEST, "해당 글 삭제/수정 권한이 없습니다."),
    NOT_EXIST_RESOURCE(BAD_REQUEST, "해당 게시글은 존재하지 않습니다");

    private final HttpStatus httpStatus;
    private final String detail;
}


/**
 *      400 BAD_REQUEST : 잘 못 된 접근
 *      MISMATCH_REFRESH_TOKEN(BAD_REQUEST, "리프레시 토큰의 유저 정보가 일치하지 않습니다"),
 *      CANNOT_FOLLOW_MYSELF(BAD_REQUEST, "자기 자신은 팔로우 할 수 없습니다"),
 *
 *      401 UNAUTHORIZED : 인증되지 않은 사용자
 *     INVALID_AUTH_TOKEN(UNAUTHORIZED, "권한 정보가 없는 토큰입니다"),
 *     UNAUTHORIZED_MEMBER(UNAUTHORIZED, "현재 내 계정 정보가 존재하지 않습니다"),
 *
 *      404 NOT_FOUND : Resource 를 찾을 수 없음
 *     MEMBER_NOT_FOUND(NOT_FOUND, "해당 유저 정보를 찾을 수 없습니다"),
 *     REFRESH_TOKEN_NOT_FOUND(NOT_FOUND, "로그아웃 된 사용자입니다"),
 *     NOT_FOLLOW(NOT_FOUND, "팔로우 중이지 않습니다"),
 *
 *      409 CONFLICT : Resource 의 현재 상태와 충돌. 보통 중복된 데이터 존재
 *     DUPLICATE_RESOURCE(CONFLICT, "데이터가 이미 존재합니다");
 */

