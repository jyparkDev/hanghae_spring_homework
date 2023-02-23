package com.sparta.boardbasic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing // Timestamped에 생성시간, 수정시간 관련하여 해당 어노테이션 지정해주어야한다.
public class BoardbasicApplication {

    public static void main(String[] args) {
        SpringApplication.run(BoardbasicApplication.class, args);
    }

}
