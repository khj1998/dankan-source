package com.dankan.service.report;

import com.dankan.domain.*;
import com.dankan.dto.request.report.ReviewReportRequestDto;
import com.dankan.dto.response.report.ReportResponseDto;
import com.dankan.dto.request.report.RoomReportRequestDto;
import com.dankan.exception.post.PostNotFoundException;
import com.dankan.exception.report.PostReportNotFoundException;
import com.dankan.exception.report.ReviewReportNotFoundException;
import com.dankan.exception.review.ReviewNotFoundException;
import com.dankan.exception.room.RoomNotFoundException;
import com.dankan.repository.*;
import com.dankan.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.UUID;

@Slf4j
public class ReportServiceImpl implements ReportService {

    private final PostReportRepository postReportRepository;
    private final ReviewReportRepository reviewReportRepository;
    private final PostRepository postRepository;
    private final RoomRepository roomRepository;
    private final ReviewRepository reviewRepository;
    private final DateLogRepository dateLogRepository;

    public ReportServiceImpl(PostReportRepository postReportRepository
                      ,ReviewReportRepository reviewReportRepository
                      ,PostRepository postRepository
                      ,RoomRepository roomRepository
                      ,ReviewRepository reviewRepository
                      ,DateLogRepository dateLogRepository) {
        this.postReportRepository = postReportRepository;
        this.reviewReportRepository = reviewReportRepository;
        this.postRepository = postRepository;
        this.roomRepository = roomRepository;
        this.reviewRepository = reviewRepository;
        this.dateLogRepository = dateLogRepository;
    }

    @Override
    @Transactional
    public Boolean addPostReport(RoomReportRequestDto roomReportRequestDto) {
        Long userId = JwtUtil.getMemberId();
        Post post = postRepository.findById(roomReportRequestDto.getPostId())
                .orElseThrow(() -> new PostNotFoundException(roomReportRequestDto.getPostId()));

        Long roomId = post.getRoomId();

        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RoomNotFoundException(roomId));

        DateLog dateLog = DateLog.builder()
                .userId(userId)
                .createdAt(LocalDate.now())
                .lastUserId(userId)
                .updatedAt(LocalDate.now())
                .build();
        dateLogRepository.save(dateLog);

        PostReport postReport = PostReport.of(room, userId,dateLog.getId());
        postReportRepository.save(postReport);

        return true;
    }

    @Override
    @Transactional
    public Boolean addReviewReport(ReviewReportRequestDto reviewReportRequestDto) {
        Long userId = JwtUtil.getMemberId();

        RoomReview roomReview = reviewRepository.findById(reviewReportRequestDto.getReviewId())
                .orElseThrow(() -> new ReviewNotFoundException(reviewReportRequestDto.getReviewId()));

        DateLog dateLog = DateLog.builder()
                .userId(userId)
                .createdAt(LocalDate.now())
                .lastUserId(userId)
                .updatedAt(LocalDate.now())
                .build();
        dateLogRepository.save(dateLog);

        ReviewReport reviewReport = ReviewReport.of(userId,dateLog.getId(),roomReview);
        reviewReportRepository.save(reviewReport);

        return true;
    }

    @Override
    @Transactional
    public ReportResponseDto findPostReport(Long reportId) {
        PostReport postReport = postReportRepository.findById(reportId)
                .orElseThrow(() -> new PostReportNotFoundException(reportId));

        return ReportResponseDto.of(postReport);
    }

    @Override
    @Transactional
    public void removePostReport(Long reportId) {
        postReportRepository.deleteById(reportId);
    }

    @Override
    @Transactional
    public ReportResponseDto findReviewReport(Long reportId) {
        ReviewReport reviewReport = reviewReportRepository.findById(reportId)
                .orElseThrow(() -> new ReviewReportNotFoundException(reportId));

        return ReportResponseDto.of(reviewReport);
    }

    @Override
    @Transactional
    public void removeReviewReport(Long reportId) {
        reviewReportRepository.deleteById(reportId);
    }
}
