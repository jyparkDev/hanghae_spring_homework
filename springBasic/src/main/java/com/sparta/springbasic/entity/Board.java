package com.sparta.springbasic.entity;

import com.sparta.springbasic.dto.BoardRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class Board extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; // 고유번호

    @Column(nullable = false)
    private String title; // 글 제목


    @Column(nullable = false)
    private String content; // 글 내용

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Board(String title,String content,User user) {
        this.title = title;
        this.content = content;
        this.user = user;
    }

    public Board updateBoard(String title, String content){
        this.title = title;
        this.content = content;
        return this;
    }


}


