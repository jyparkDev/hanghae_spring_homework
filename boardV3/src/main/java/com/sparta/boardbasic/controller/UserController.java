package com.sparta.boardbasic.controller;

import com.sparta.boardbasic.dto.requestDto.LoginRequestDto;
import com.sparta.boardbasic.dto.requestDto.SignUpRequestDto;
import com.sparta.boardbasic.dto.responseDto.StatusMsgResponseDto;
import com.sparta.boardbasic.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 회원 관련 Controller
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth") // 공통 경로 지정
public class UserController {
    private final UserService userService;

    /**
     * 회원가입 기능 Controller
     */
    @PostMapping("/signup")
    public ResponseEntity<StatusMsgResponseDto> signup(@Valid @RequestBody SignUpRequestDto requestDto, BindingResult result){
        // 유저 계정, 패스워드가 규칙에 맞지 않을 경우
        if(result.hasErrors()){
            return ResponseEntity.badRequest()
                    .body(new StatusMsgResponseDto(result.getFieldError().getDefaultMessage(), HttpStatus.BAD_REQUEST.value()));
        }
        return userService.signup(requestDto);
    }

    /**
     * 로그인 기능 Controller
     */
    @PostMapping("/login")  // ResponseEntity 응답 객체 사용으로 HttpServletResponse res 매개변수 필요없음
    public ResponseEntity<StatusMsgResponseDto> login(@RequestBody LoginRequestDto loginRequestDto){
        return userService.login(loginRequestDto);
    }
}
