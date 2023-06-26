package com.dankan.service.report;

import com.dankan.dto.response.report.ReviewReportResponseDto;
import com.dankan.dto.response.report.RoomReportResponseDto;
import com.dankan.dto.request.report.ReviewReportRequestDto;
import com.dankan.dto.request.report.RoomReportRequestDto;

public interface ReportService {
    RoomReportResponseDto addPostReport(RoomReportRequestDto roomReportRequestDto);
    ReviewReportResponseDto addReviewReport(ReviewReportRequestDto reviewReportRequestDto);
}
