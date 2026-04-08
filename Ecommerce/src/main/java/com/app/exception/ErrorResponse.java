package com.app.exception;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ErrorResponse {
	
	private String message;
    private Object details;

    public ErrorResponse(String message, Object details) {
        this.message = message;
        this.details = details;
    }

}
