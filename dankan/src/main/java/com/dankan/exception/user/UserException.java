package com.dankan.exception.user;

import com.dankan.exception.DefaultException;
import com.dankan.exception.code.Code;

public class UserException extends DefaultException {
    public UserException(final Code code) {
        super(code);
    }
}
