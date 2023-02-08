package com.sparta.springbasic.entity;

import com.sparta.springbasic.dto.BoardRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class Board extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; // 고유번호

    @Column(nullable = false)
    private String title; // 글 제목

    @Column(nullable = false)
    private String writer; // 작성자

    @Column(nullable = false)
    private String passwd;  // 패스워드

    @Column(nullable = false)
    private String content; // 글 내용



    public void updateBoard(BoardRequestDto requestDto){
        this.title = requestDto.getTitle();
        this.writer = requestDto.getWriter();
        this.content = requestDto.getContent();
    }


}


