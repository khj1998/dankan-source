package com.dankan.exception.code;

import org.springframework.http.HttpStatus;

public interface Code {
    String name();
    HttpStatus getCode();
    String getMessage();
}
