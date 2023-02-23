package com.sparta.boardbasic.service;

import com.sparta.boardbasic.dto.requestDto.LoginRequestDto;
import com.sparta.boardbasic.dto.requestDto.SignUpRequestDto;
import com.sparta.boardbasic.dto.responseDto.StatusMsgResponseDto;
import com.sparta.boardbasic.entity.User;
import com.sparta.boardbasic.entity.enumSet.ErrorCodeEnum;
import com.sparta.boardbasic.entity.enumSet.UserRoleEnum;
import com.sparta.boardbasic.exception.CustomException;
import com.sparta.boardbasic.jwt.JwtUtil;
import com.sparta.boardbasic.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * 회원 관련 Service
 */
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final String ADMIN_TOKEN = "aaaa";

    /**
     * 회원가입 기능
     */
    @Transactional
    public ResponseEntity<StatusMsgResponseDto> signup(SignUpRequestDto requestDto) {
        // 해당 회원이 있는 지 조회
        Optional<User> findUser = userRepository.findByUsername(requestDto.getUsername());
        // 해당 회원이 있다면 예외발생 
        if(findUser.isPresent()){
            throw new CustomException(ErrorCodeEnum.DUPLICATE_MEMBER);
        }
        UserRoleEnum role = UserRoleEnum.USER;
        if(requestDto.isAdminCheck()){
            if(!requestDto.getAdminToken().equals(ADMIN_TOKEN)){
                throw new CustomException(ErrorCodeEnum.DUPLICATE_MEMBER);
            }
            role = UserRoleEnum.ADMIN;
        }
        // 회원 entiry 생성
        User user = User.builder()
                .username(requestDto.getUsername())
                .password(requestDto.getPassword())
                .role(role)
                .build();
        // 회원 저장
        userRepository.save(user);
        return ResponseEntity.ok(new StatusMsgResponseDto("회원가입 완료",HttpStatus.OK.value()));
    }

    /**
     * 로그인 기능 구현
     */

    public ResponseEntity<StatusMsgResponseDto> login(LoginRequestDto loginRequestDto) { // ResponseEntity 응답 객체 사용으로 HttpServletResponse res 매개변수 필요없음
        // 등록된 회원인지 DB에서 찾아오기
        // loginRequestDto를 통하여 로그인 시 입력한 username를 통해서 검색
        Optional<User> findUser = userRepository.findByUsername(loginRequestDto.getUsername());

        // 찾은 회원이 없을 시 에러메세지 전달
        if(findUser.isEmpty()){
            throw new CustomException(ErrorCodeEnum.MEMBER_NOT_FOUND);
        }
        // 회원 비밀번호 맞는지 확인
        if(!findUser.get().getPassword().equals(loginRequestDto.getPassword())){
            throw new CustomException(ErrorCodeEnum.MEMBER_NOT_FOUND);
        }

        // 회원이 있다면 Token 발급 받기
        String token = jwtUtil.createToken(findUser.get().getUsername(), findUser.get().getRole());
        
        /* Response Header에 토큰 넣기
         ResponseEntity 사용으로 더 이상 HttpServletResponse 객체 사용할 필요 X */
        // res.addHeader(JwtUtil.AUTHORIZATION_HEADER,token);

        // 성공 메세지 전달
        return ResponseEntity.ok()
                 /*
                    res.addHeader(JwtUtil.AUTHORIZATION_HEADER,token) << 이 부분을
                    ResponseEntity를 통하여 header(키, 값) 활용하여 response 응답 헤더부분에 토큰 값 넣기
                */
                .header(JwtUtil.AUTHORIZATION_HEADER, token)
                .body( new StatusMsgResponseDto("로그인 성공" , HttpStatus.OK.value()));
    }
}
