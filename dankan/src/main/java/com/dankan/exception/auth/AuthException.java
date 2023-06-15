package com.dankan.exception.auth;

import com.dankan.exception.DefaultException;
import com.dankan.exception.code.Code;


public class AuthException extends DefaultException {
    public AuthException(Code code) {
        super(code);
    }
}
