package com.sparta.springbasic.dto;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
public class SignupRequestDto {

    @Size(min = 4,max = 10, message = "계정명은 4~10자리만 가능합니다")
    @Pattern(regexp = "^[a-z0-9]{4,10}$",message = "계정명은 소문자, 숫자만 가능합니다.")
    private String username;

    @Size(min = 8,max = 15, message = "패스워드는 8~15자리만 가능합니다")
    @Pattern(regexp = "^[a-zA-Z0-9\\\\d`~!@#$%^&*()-_=+]{8,15}$",message = "패스워드는 대/소문자, 숫자만 가능합니다.")
    private String password;

    private boolean admin = false;
    private String adminToken = "";

    @Builder
    public SignupRequestDto(String username, String password, boolean admin, String adminToken) {
        this.username = username;
        this.password = password;
        this.admin = admin;
        this.adminToken = adminToken;
    }
}
