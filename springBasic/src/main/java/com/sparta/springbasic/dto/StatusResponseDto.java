package com.sparta.springbasic.dto;

import com.sparta.springbasic.entity.User;
import lombok.Getter;

@Getter

public class StatusResponseDto {

    private int statusCode;
    private String statusMsg;


    public StatusResponseDto(int statusCode, String statusMsg) {
        this.statusCode = statusCode;
        this.statusMsg = statusMsg;
    }
}
