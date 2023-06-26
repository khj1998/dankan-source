package com.dankan.exception.post;

import com.dankan.exception.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@Getter
@ResponseStatus(HttpStatus.NOT_FOUND)
public class PostNotFoundException extends RuntimeException {
    private String message;
    private ErrorCode code;

    public PostNotFoundException(Long id) {
        super(id.toString());
        this.message = id.toString();
    }
}
