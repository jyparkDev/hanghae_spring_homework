package com.sparta.springbasic.entity;

import com.sparta.springbasic.dto.SignupRequestDto;
import lombok.*;
import org.hibernate.query.criteria.internal.expression.function.AggregationFunction;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Getter
@Entity(name="users")
@NoArgsConstructor
@ToString
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    @Builder
    public User(SignupRequestDto requestDto) {
        this.username = requestDto.getUsername();
        this.password = requestDto.getPassword();
        role = requestDto.isAdminCheck() ? UserRoleEnum.ADMIN : UserRoleEnum.USER;
    }
}
