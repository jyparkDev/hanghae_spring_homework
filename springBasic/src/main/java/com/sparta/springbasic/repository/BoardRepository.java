package com.sparta.springbasic.repository;

import com.sparta.springbasic.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board,Long> {

}
