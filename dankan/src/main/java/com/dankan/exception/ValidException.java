package com.dankan.exception;

import com.dankan.exception.code.Code;

public class ValidException extends DefaultException {
    public ValidException(Code code) {
        super(code);
    }
}
