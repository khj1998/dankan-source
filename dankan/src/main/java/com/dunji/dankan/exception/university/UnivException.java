package com.dunji.dankan.exception.university;

import com.dunji.dankan.exception.DefaultException;
import com.dunji.dankan.exception.code.Code;

public class UnivException extends DefaultException {
    public UnivException(Code code) {
        super(code);
    }
}
