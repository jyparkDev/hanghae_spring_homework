package com.sparta.springbasic.repository;

import com.sparta.springbasic.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board,Long> {
    /**
     * 게시글 등록일 내림차순 정렬
     * @return
     */
    List<Board> findAllByOrderByModifiedAtDesc();
    List<Board> findAllByUserIdOrderByModifiedAtDesc(Long userId);
    Optional<Board> findByIdAndUserId(Long id,Long userId);

}
