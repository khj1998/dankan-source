package com.dunji.dankan.exception;

import com.dunji.dankan.exception.code.Code;

public class ValidException extends DefaultException {
    public ValidException(Code code) {
        super(code);
    }
}
