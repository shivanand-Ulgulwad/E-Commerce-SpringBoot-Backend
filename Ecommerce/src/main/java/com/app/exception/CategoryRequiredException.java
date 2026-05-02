package com.app.exception;

public class CategoryRequiredException extends RuntimeException{
    public CategoryRequiredException(String msg) {
        super(msg);
    }
}
