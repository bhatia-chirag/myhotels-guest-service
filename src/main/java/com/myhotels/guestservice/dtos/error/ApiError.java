package com.myhotels.guestservice.dtos.error;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ApiError {
    private String message;
    private String rootCause;
    private LocalDateTime dateTime;

    public ApiError(String message, String rootCause) {
        this.message = message;
        this.rootCause = rootCause;
        dateTime = LocalDateTime.now();
    }
}
