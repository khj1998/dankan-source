package com.dankan.exception.report;

import com.dankan.exception.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@Getter
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ReviewReportNotFoundException extends RuntimeException {
    private String message;
    private ErrorCode errorCode;

    public ReviewReportNotFoundException(Long reportId) {
        super(reportId.toString());
        this.message = reportId.toString();
    }

    public ReviewReportNotFoundException(String address) {
        super(address);
        this.message = address;
    }
}
