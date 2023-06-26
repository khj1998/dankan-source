package com.dankan.service.report;

import com.dankan.dto.response.report.ReportResponseDto;
import com.dankan.dto.request.report.ReviewReportRequestDto;
import com.dankan.dto.request.report.RoomReportRequestDto;

import java.util.UUID;

public interface ReportService {
    ReportResponseDto addPostReport(RoomReportRequestDto roomReportRequestDto);
    ReportResponseDto findPostReport(UUID reportId);
    void removePostReport(UUID reportId);
    ReportResponseDto findReviewReport(UUID reportId);
    void removeReviewReport(UUID reportId);
    ReportResponseDto addReviewReport(ReviewReportRequestDto reviewReportRequestDto);
}
