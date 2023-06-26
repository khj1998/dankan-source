package com.dankan.exception.review;

import com.dankan.exception.ErrorCode;

import java.util.UUID;

public class ReviewNotFoundException extends RuntimeException {
    private String message;
    private ErrorCode code;

    public ReviewNotFoundException(Long id) {
        super(id.toString());
        this.message = id.toString();
    }
}
