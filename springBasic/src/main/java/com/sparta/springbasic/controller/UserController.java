package com.sparta.springbasic.controller;


import com.sparta.springbasic.dto.SignupRequestDto;
import com.sparta.springbasic.dto.StatusResponseDto;
import com.sparta.springbasic.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    /**
     * 회원가입 페이지
     */
    @GetMapping("/signup")
    public ModelAndView signupPage() {
        return new ModelAndView("signup");
    }

    /**
     * 로그인 페이지
     */
    @GetMapping("/login")
    public ModelAndView loginPage() {
        return new ModelAndView("login");
    }

    /**
     * 회원가입
     */
    @ResponseBody
    @PostMapping("/signup")
    public StatusResponseDto signup(@Valid @RequestBody SignupRequestDto signupRequestDto, BindingResult result) {
        /**
         * 회원가입 정보 유효성 확인
         */
        if(result.hasErrors()){
            return new StatusResponseDto("4xx",result.getFieldError().getDefaultMessage());
        }
        return userService.signup(signupRequestDto);
    }


}