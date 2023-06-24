package com.dankan.dto.response.report;

import lombok.*;

import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewReportResponseDto {
    private Boolean isReportSuccess;

    public static ReviewReportResponseDto of(Boolean isSuccess) {
        return ReviewReportResponseDto.builder()
                .isReportSuccess(isSuccess)
              .build();
    }
}
