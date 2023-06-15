package com.dankan.exception;

import com.dankan.exception.code.Code;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class DefaultException extends RuntimeException {
    private final Code code;
}
