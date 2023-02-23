package com.sparta.boardbasic.dto.requestDto;

import lombok.Getter;

import javax.validation.constraints.Pattern;

/**
 * 회원가입 시 받을 정보를 위한 RequestDto
 */
@Getter
public class SignUpRequestDto {
    @Pattern(regexp = "^[a-z0-9]{4,10}$",message = "계정명은 4~10자 사이 소문자,숫자만 가능합니다")
    private String username;// 계정명
    @Pattern(regexp = "^[a-zA-Z0-9\\\\d`~!@#$%^&*()-_=+]{8,15}$",message = "패스워드는 8~15자 사이 대/소문자,숫자만 가능합니다")
    private String password; // 패스워드
    private boolean adminCheck; // 관리자 여부
    private String adminToken; // 관리자 확인 번호
}
