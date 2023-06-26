package com.dankan.exception.report;

import com.dankan.exception.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@Getter
@ResponseStatus(HttpStatus.NOT_FOUND)
public class PostReportNotFoundException extends RuntimeException {
    private String message;
    private ErrorCode code;

    public PostReportNotFoundException(UUID id) {
        super(id.toString());
        this.message = id.toString();
    }

    public PostReportNotFoundException(String address) {
        super(address);
        this.message = address;
    }
}
