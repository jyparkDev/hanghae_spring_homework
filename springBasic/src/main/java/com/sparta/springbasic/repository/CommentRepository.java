package com.sparta.springbasic.repository;

import com.sparta.springbasic.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Long> {
}
