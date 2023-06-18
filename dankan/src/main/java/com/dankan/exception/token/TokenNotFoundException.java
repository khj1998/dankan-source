package com.dankan.exception.token;

import com.dankan.exception.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(HttpStatus.NOT_FOUND)
public class TokenNotFoundException extends RuntimeException {
    private String message;
    private ErrorCode code;

    public TokenNotFoundException(String userId) {
        super(userId);
        message = userId.toString() + " is not exist";
    }
}
