package com.sparta.boardbasic.dto.responseDto;

import lombok.Builder;
import lombok.Getter;

/**
 * 상태 메세지와 상태 코드를 응답하기 위한 ResponseDto
 */
@Getter
public class StatusMsgResponseDto {

    private String msg; // 상태 메세지
    private Integer msgCode; // 상태 코드

    @Builder
    public StatusMsgResponseDto(String msg, Integer msgCode) {
        this.msg = msg;
        this.msgCode = msgCode;
    }
}
