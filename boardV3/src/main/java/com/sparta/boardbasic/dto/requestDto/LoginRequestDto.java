package com.sparta.boardbasic.dto.requestDto;

import lombok.Getter;

/**
 * 로그인 시 서버로 전달되는 값을 담기 위한 RequestDto
 */
@Getter
public class LoginRequestDto {
    private String username; // 회원명
    private String password; // 비밀번호
}
