package com.app.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter

@NoArgsConstructor
public class ErrorResponse {

    private LocalDateTime timestamp ;
    private int status;
    private String message;
    private Object errors;

    public ErrorResponse(int status, String message, Object errors){
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.message = message;
        this.errors = errors;
    }



}
