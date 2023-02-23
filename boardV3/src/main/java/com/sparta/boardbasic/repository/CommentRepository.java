package com.sparta.boardbasic.repository;

import com.sparta.boardbasic.entity.Board;
import com.sparta.boardbasic.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findAllByBoardOrderByCreatedAtDesc(Board board);

    void deleteAllByBoard(Board board);
}
