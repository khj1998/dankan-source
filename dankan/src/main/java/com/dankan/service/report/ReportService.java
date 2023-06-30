package com.dankan.service.report;

import com.dankan.dto.response.report.ReportResponseDto;
import com.dankan.dto.request.report.ReviewReportRequestDto;
import com.dankan.dto.request.report.RoomReportRequestDto;

public interface ReportService {
    Boolean addPostReport(RoomReportRequestDto roomReportRequestDto);
    ReportResponseDto findPostReport(Long reportId);
    void removePostReport(Long  reportId);
    ReportResponseDto findReviewReport(Long  reportId);
    void removeReviewReport(Long  reportId);
    Boolean addReviewReport(ReviewReportRequestDto reviewReportRequestDto);
}
