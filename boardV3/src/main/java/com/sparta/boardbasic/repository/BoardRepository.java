package com.sparta.boardbasic.repository;

import com.sparta.boardbasic.entity.Board;
import com.sparta.boardbasic.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/*** User Repository*/
public interface BoardRepository extends JpaRepository<Board,Long> {
    List<Board> findAllByOrderByModifiedAtDesc();
    Optional<Board> findByIdAndUser(Long id, User user);
}
