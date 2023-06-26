package com.dankan.dto.response.report;

import com.dankan.domain.PostReport;
import com.dankan.domain.ReviewReport;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReportResponseDto {
    private Boolean isSuccess;
    private UUID userId;
    private String reportType;
    private String address;
    private String addressDetail;

    public static ReportResponseDto of(Boolean isSuccess) {
        return ReportResponseDto.builder()
                .isSuccess(isSuccess)
                .build();
    }

    public static ReportResponseDto of(PostReport postReport) {
        return ReportResponseDto.builder()
                .userId(postReport.getUserId())
                .reportType(postReport.getReportType())
                .address(postReport.getAddress())
                .addressDetail(postReport.getAddressDetail())
                .build();
    }

    public static ReportResponseDto of(ReviewReport reviewReport) {
        return ReportResponseDto.builder()
                .userId(reviewReport.getUserId())
                .reportType(reviewReport.getReportType())
                .address(reviewReport.getAddress())
                .addressDetail(reviewReport.getAddressDetail())
                .build();
    }
}
