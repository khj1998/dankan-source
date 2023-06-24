package com.dankan.exception.user;

import com.dankan.exception.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(HttpStatus.NOT_FOUND)
public class PhoneNumberNotFoundException extends RuntimeException {
    private String message;
    private ErrorCode code;

    public PhoneNumberNotFoundException(String ph) {
        super(ph);
        this.message = ph;
    }
}
