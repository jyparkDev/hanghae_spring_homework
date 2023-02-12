package com.sparta.springbasic.repository;

import com.sparta.springbasic.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    /** 계정명으로 회원 조회 */
    Optional<User> findByUsername(String userName);
}
