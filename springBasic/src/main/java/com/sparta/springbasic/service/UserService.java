package com.sparta.springbasic.service;

import com.sparta.springbasic.dto.SignupRequestDto;
import com.sparta.springbasic.dto.StatusResponseDto;
import com.sparta.springbasic.entity.User;
import com.sparta.springbasic.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;


    /**
     * 회원가입 기능
     * @param signupRequestDto
     * @return
     */
    @Transactional
    public StatusResponseDto signup(SignupRequestDto signupRequestDto) {
        String name = signupRequestDto.getUsername(); // 가입할 회원명
        String passwd = signupRequestDto.getPassword(); // 가입할 패스워드

        //동일한 회원명 있는지 확인
        Optional<User> findUser = userRepository.findByUsername(name);
        if(findUser.isPresent()){
            try {
                throw new IllegalStateException("이미 존재하는 계정입니다.");
            }catch (IllegalStateException e){
                return new StatusResponseDto("4xx", e.getMessage());
            }
        }

        // 회원 등록
        User user = User.builder()
                .username(name)
                .password(passwd)
                .build();
        userRepository.save(user);

        return new StatusResponseDto("200","회원가입 성공!");
    }
}
