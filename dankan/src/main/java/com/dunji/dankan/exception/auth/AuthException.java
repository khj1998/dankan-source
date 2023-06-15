package com.dunji.dankan.exception.auth;

import com.dunji.dankan.exception.DefaultException;
import com.dunji.dankan.exception.code.Code;


public class AuthException extends DefaultException {
    public AuthException(Code code) {
        super(code);
    }
}
