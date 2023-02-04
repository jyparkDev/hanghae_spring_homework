package com.sparta.springbasic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SpringBasicApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBasicApplication.class, args);
    }

}
