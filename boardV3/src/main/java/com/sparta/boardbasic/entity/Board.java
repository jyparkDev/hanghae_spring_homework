package com.sparta.boardbasic.entity;

import com.sparta.boardbasic.dto.requestDto.BoardRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/*
    Entity로 실제 DB에 저장되는 객체
    게시글 Entity
*/
@Entity
@NoArgsConstructor
@Getter
public class Board extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 게시글 번호
    private String title; // 제목
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user; // 유저
    private String content; // 글내용

    /**
     * 빌더패턴을 적용하여 생성자를 통해 값 받기
     */
    @Builder
    public Board(BoardRequestDto requestDto, User user) {
        this.title = requestDto.getTitle();
        this.user = user;
        this.content = requestDto.getContent();
    }

    public void update(BoardRequestDto requestDto){
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
    }
}
