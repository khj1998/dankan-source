package com.dunji.dankan.exception;

import com.dunji.dankan.exception.code.Code;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class DefaultException extends RuntimeException {
    private final Code code;
}
