package com.dankan.exception.options;

import com.dankan.exception.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(HttpStatus.NOT_FOUND)
public class OptionNotFoundException extends  RuntimeException {
    private String message;
    private ErrorCode code;

    public OptionNotFoundException(String codeKey) {
        super(codeKey);
        this.message = codeKey;
    }
}
