package com.sparta.springbasic.service;

import com.sparta.springbasic.dto.LoginRequestDto;
import com.sparta.springbasic.dto.SignupRequestDto;
import com.sparta.springbasic.dto.StatusResponseDto;
import com.sparta.springbasic.entity.User;
import com.sparta.springbasic.entity.UserRoleEnum;
import com.sparta.springbasic.jwt.JwtUtil;
import com.sparta.springbasic.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    /**
     * 회원가입 기능
     */
    @Transactional
    public ResponseEntity<StatusResponseDto> signup(SignupRequestDto signupRequestDto) {
        //동일한 회원명 있는지 확인
        Optional<User> findUser = userRepository.findByUsername(signupRequestDto.getUsername());
        if(findUser.isPresent()){
            return exceptionResponse("이미 존재하는 계정입니다.");
        }
        // 회원 등록
        User user = User.builder()
                .requestDto(signupRequestDto)
                .build();
        userRepository.save(user);
        return ResponseEntity.ok(new StatusResponseDto(HttpStatus.OK.value(),"회원가입 성공!"));
    }


    /**
     * 로그인 기능
     */
    @Transactional(readOnly = true)
    public ResponseEntity<StatusResponseDto> login(LoginRequestDto loginRequestDto) {
        String name = loginRequestDto.getUsername();
        String passwd = loginRequestDto.getPassword();

        Optional<User> user = userRepository.findByUsername(name);
        if(user.isEmpty()){
            return exceptionResponse("유효하지 않은 계정입니다.");
        }else if(!(user.get().getPassword().equals(passwd))){
            return exceptionResponse("패스워드가 일치하지 않습니다.");
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add(JwtUtil.AUTHORIZATION_HEADER,jwtUtil.createToken(user.get().getUsername(),user.get().getRole()));
        return ResponseEntity.ok()
                .headers(headers)
                .body(new StatusResponseDto(HttpStatus.OK.value(),"로그인 성공!!"));
    }

    /**
     * 공통 예외처리 기능
     */
    private ResponseEntity<StatusResponseDto> exceptionResponse(String msg) {
        return ResponseEntity.badRequest()
                .body(new StatusResponseDto(HttpStatus.BAD_REQUEST.value(), msg));
    }
}
