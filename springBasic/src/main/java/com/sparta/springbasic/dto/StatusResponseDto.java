package com.sparta.springbasic.dto;

import com.sparta.springbasic.entity.User;
import lombok.Getter;

@Getter
public class StatusResponseDto {

    private String statusCode;
    private String statusMsg;


    public StatusResponseDto(String statusCode, String statusMsg) {
        this.statusCode = statusCode;
        this.statusMsg = statusMsg;
    }
}
