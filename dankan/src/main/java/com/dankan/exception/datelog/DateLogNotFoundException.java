package com.dankan.exception.datelog;

import com.dankan.exception.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(HttpStatus.NOT_FOUND)
public class DateLogNotFoundException extends RuntimeException {

    private String message;
    private ErrorCode code;

    public DateLogNotFoundException(Long id) {
        super(id.toString());
        this.message = id.toString();
    }
}
