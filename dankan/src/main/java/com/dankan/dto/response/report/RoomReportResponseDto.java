package com.dankan.dto.response.report;

import com.dankan.domain.PostReport;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomReportResponseDto {
    private Boolean isReportSuccess;

    public static RoomReportResponseDto of(Boolean isSuccess) {
        return RoomReportResponseDto.builder()
                .isReportSuccess(isSuccess)
                .build();
    }
}
