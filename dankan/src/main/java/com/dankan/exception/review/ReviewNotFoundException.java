package com.dankan.exception.review;

import com.dankan.exception.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@Getter
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ReviewNotFoundException extends RuntimeException {
    private String message;
    private ErrorCode code;

    public ReviewNotFoundException(Long id) {
        super(id.toString());
        this.message = id.toString();
    }

    public ReviewNotFoundException(String address) {
        super(address);
        this.message = address;
    }
}
