package com.dankan.exception.image;

import com.dankan.exception.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ImageNotFoundException extends RuntimeException {
    private String message;
    private ErrorCode errorCode;

    public ImageNotFoundException(Long Id) {
        super(Id.toString());
        this.message = Id.toString();
    }
}
