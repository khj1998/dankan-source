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
            @ApiResponse(responseCode = "200",description = "매물 게시물 신고 등록 성공 "),
            @ApiResponse(responseCode = "401",description = "인증되지 않은 사용자"),
            @ApiResponse(responseCode = "403",description = "유저가 Member | Admin 권한이 없음"),
            @ApiResponse(responseCode = "404",description = "매물 게시물 신고 등록에 실패함")
    })
    @PostMapping("/post")
    public ResponseEntity<ReportResponseDto> addPostReport(@RequestBody RoomReportRequestDto roomReportRequestDto) {
        ReportResponseDto responseDto = reportService.addPostReport(roomReportRequestDto);
        return ResponseEntity.ok(responseDto);
    }

    @ApiOperation("매물 리뷰 신고 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "매물 리뷰 신고 등록 성공 "),
            @ApiResponse(responseCode = "401",description = "인증되지 않은 사용자"),
            @ApiResponse(responseCode = "403",description = "유저가 Member | Admin 권한이 없음"),
            @ApiResponse(responseCode = "404",description = "매물 리뷰 신고 등록에 실패함")
    })
    @PostMapping("/review")
    public ResponseEntity<ReportResponseDto> addReviewReport(@RequestBody ReviewReportRequestDto reviewReportRequestDto) {
        ReportResponseDto responseDto = reportService.addReviewReport(reviewReportRequestDto);
        return ResponseEntity.ok(responseDto);
    }
}
