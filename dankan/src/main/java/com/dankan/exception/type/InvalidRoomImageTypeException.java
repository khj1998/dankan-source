package com.dankan.exception.type;

import com.dankan.exception.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidRoomImageTypeException extends RuntimeException {
    private String message;
    private ErrorCode code;

    public InvalidRoomImageTypeException(String inputValue) {
        super(inputValue);
        this.message = inputValue;
    }
}
