package com.dankan.exception.user;

import com.dankan.exception.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(HttpStatus.NOT_FOUND)
public class EmailNotFoundException extends RuntimeException {
    private String message;
    private ErrorCode code;

    public EmailNotFoundException(String email) {
        super(email);
        this.message = email;
    }
}
