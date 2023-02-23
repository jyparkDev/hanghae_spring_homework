package com.sparta.boardbasic.entity;

import com.sparta.boardbasic.entity.enumSet.UserRoleEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * 회원 Entity
 */
@Entity(name = "users") //User라는 예약어가 있기에 다른 이름으로 해야한다.
@NoArgsConstructor
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false) // null 값 허용하지 않는다
    private String username; // 회원명
    @Column(nullable = false) // null 값 허용하지 않는다
    private String password; // 비밀번호


    /*
        UserRoleEnum -> USER(0) , ADMIN(1)
        EnumType.STRING 속성 -> DB에 enum에 있는 문자 그대로 저장, 이거 사용해야한다.
        EnumType.ORDINAL 속성
        -> DB에 숫자로 저장 , UserRoleEnum 중간에 속성이 추가되면
        예를들어 USER(0) ,MAN(1) ADMIN(2)으로 추가되면 ADMIN이 이때부터 2로 저장되서 기존에 데이터와 불일치가 발생
        이 속성 사용하면 안된다.
    */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRoleEnum role;

    @Builder
    public User(String username, String password, UserRoleEnum role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }
}
