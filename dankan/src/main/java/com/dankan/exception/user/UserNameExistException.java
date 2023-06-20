package com.dankan.exception.user;

import com.dankan.exception.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserNameExistException extends RuntimeException {
    private String message;
    private ErrorCode code;

    public UserNameExistException(String name) {
        super(name);
        message = name;
    }
}
