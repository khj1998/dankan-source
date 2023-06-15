package com.dankan.exception.university;

import com.dankan.exception.DefaultException;
import com.dankan.exception.code.Code;

public class UnivException extends DefaultException {
    public UnivException(Code code) {
        super(code);
    }
}
