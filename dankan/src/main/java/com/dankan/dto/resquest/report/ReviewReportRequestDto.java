package com.dankan.dto.resquest.report;

import lombok.Getter;

import java.util.UUID;

@Getter
public class ReviewReportRequestDto {
    private UUID reviewId;
    private String reportType;
}
