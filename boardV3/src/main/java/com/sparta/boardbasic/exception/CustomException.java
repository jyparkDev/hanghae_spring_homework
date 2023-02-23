package com.sparta.boardbasic.exception;

import com.sparta.boardbasic.entity.enumSet.ErrorCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Custom한 예외생성 클래스
 */
@Getter
@AllArgsConstructor
public class CustomException extends RuntimeException{
    private final ErrorCodeEnum errorCode;
}
