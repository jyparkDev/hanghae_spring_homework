package com.sparta.boardbasic.dto.responseDto;

import com.sparta.boardbasic.entity.enumSet.ErrorCodeEnum;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

@Getter
@Builder
public class ErrorResponse {
    private final LocalDateTime timestamp = LocalDateTime.now(); // 에러시간
    private final int status; // Error 상태번호
    private final String error; // Error명
    private final String code; // Error Code
    private final String message; // Error 메세지

    // 정적 팩토리 메서드 패턴 적용
    public static ResponseEntity<ErrorResponse> toResponseEntity(ErrorCodeEnum errorCode) {
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(ErrorResponse.builder()
                        .status(errorCode.getHttpStatus().value())
                        .error(errorCode.getHttpStatus().name())
                        .code(errorCode.name())
                        .message(errorCode.getDetail())
                        .build()
                );
    }
}
