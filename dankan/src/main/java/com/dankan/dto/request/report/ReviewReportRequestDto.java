package com.dankan.dto.request.report;

import lombok.Getter;

import java.util.UUID;

@Getter
public class ReviewReportRequestDto {
    private Long reviewId;
    private String reportType;
}
