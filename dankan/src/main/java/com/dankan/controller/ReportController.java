package com.dankan.controller;

import com.dankan.dto.request.report.ReviewReportRequestDto;
import com.dankan.dto.response.report.ReportResponseDto;
import com.dankan.dto.request.report.RoomReportRequestDto;
import com.dankan.service.report.ReportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@CrossOrigin
@Api(tags = {"신고 API"})
@RequestMapping("/report")
@RestController
@RequiredArgsConstructor
public class ReportController {
    private final ReportService reportService;

    @ApiOperation("매물 게시물 신고 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "매물 게시물 신고 등록 성공 ")
    })
    @PostMapping("/post")
    public ResponseEntity<Boolean> addPostReport(@RequestBody RoomReportRequestDto roomReportRequestDto) {
        return ResponseEntity.ok(reportService.addPostReport(roomReportRequestDto));
    }

    @ApiOperation("매물 리뷰 신고 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "매물 리뷰 신고 등록 성공 ")
    })
    @PostMapping("/review")
    public ResponseEntity<Boolean> addReviewReport(@RequestBody ReviewReportRequestDto reviewReportRequestDto) {
        return ResponseEntity.ok(reportService.addReviewReport(reviewReportRequestDto));
    }
}
