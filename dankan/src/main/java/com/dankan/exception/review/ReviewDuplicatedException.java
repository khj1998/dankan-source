package com.dankan.exception.review;

import com.dankan.exception.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ReviewDuplicatedException extends RuntimeException {
    private String message;
    private ErrorCode code;

    public ReviewDuplicatedException(String address) {
        super(address);
        this.message = address;
    }
}
