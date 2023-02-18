package com.sparta.springbasic.controller;


import com.sparta.springbasic.dto.LoginRequestDto;
import com.sparta.springbasic.dto.SignupRequestDto;
import com.sparta.springbasic.dto.StatusResponseDto;
import com.sparta.springbasic.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    /**
     * 회원가입 Controller
     */
    @PostMapping("/signup")
    public ResponseEntity<StatusResponseDto> signup(@Valid @RequestBody SignupRequestDto signupRequestDto, BindingResult result) {
        //회원가입 정보 유효성 확인
        if(result.hasErrors()){
            return ResponseEntity.badRequest().body(new StatusResponseDto(HttpStatus.BAD_REQUEST.value(),result.getFieldError().getDefaultMessage()));
        }
        return userService.signup(signupRequestDto);
    }

    /**
     * 로그인 Controller
     */
    @PostMapping("/login")
    public ResponseEntity<StatusResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {
        /**
         * 회원가입 정보 유효성 확인
         */
        return userService.login(loginRequestDto);
    }

}