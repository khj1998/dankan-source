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

import java.util.UUID;

@Slf4j
public class ReportServiceImpl implements ReportService {

    private final PostReportRepository postReportRepository;
    private final ReviewReportRepository reviewReportRepository;
    private final PostRepository postRepository;
    private final RoomRepository roomRepository;
    private final ReviewRepository reviewRepository;

    public ReportServiceImpl(PostReportRepository postReportRepository
                      ,ReviewReportRepository reviewReportRepository
                      ,PostRepository postRepository
                      ,RoomRepository roomRepository
                      ,ReviewRepository reviewRepository) {
        this.postReportRepository = postReportRepository;
        this.reviewReportRepository = reviewReportRepository;
        this.postRepository = postRepository;
        this.roomRepository = roomRepository;
        this.reviewRepository = reviewRepository;
    }

    @Override
    @Transactional
    public ReportResponseDto addPostReport(RoomReportRequestDto roomReportRequestDto) {
        UUID userId = JwtUtil.getMemberId();
        Post post = postRepository.findById(roomReportRequestDto.getPostId())
                .orElseThrow(() -> new PostNotFoundException(roomReportRequestDto.getPostId()));

        UUID roomId = post.getRoomId();

        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RoomNotFoundException(roomId));

        PostReport postReport = PostReport.of(room, roomReportRequestDto.getReportType(),userId);
        postReportRepository.save(postReport);

        return ReportResponseDto.of(true);
    }

    @Override
    @Transactional
    public ReportResponseDto addReviewReport(ReviewReportRequestDto reviewReportRequestDto) {
        UUID userId = JwtUtil.getMemberId();
        RoomReview roomReview = reviewRepository.findById(reviewReportRequestDto.getReviewId())
                .orElseThrow(() -> new ReviewNotFoundException(reviewReportRequestDto.getReviewId()));

        ReviewReport reviewReport = ReviewReport.of(userId, reviewReportRequestDto.getReportType(), roomReview);
        reviewReportRepository.save(reviewReport);

        return ReportResponseDto.of(true);
    }

    @Override
    @Transactional
    public ReportResponseDto findPostReport(UUID reportId) {
        PostReport postReport = postReportRepository.findById(reportId)
                .orElseThrow(() -> new PostReportNotFoundException(reportId));

        return ReportResponseDto.of(postReport);
    }

    @Override
    @Transactional
    public void removePostReport(UUID reportId) {
        postReportRepository.deleteById(reportId);
    }

    @Override
    @Transactional
    public ReportResponseDto findReviewReport(UUID reportId) {
        ReviewReport reviewReport = reviewReportRepository.findById(reportId)
                .orElseThrow(() -> new ReviewReportNotFoundException(reportId));

        return ReportResponseDto.of(reviewReport);
    }

    @Override
    @Transactional
    public void removeReviewReport(UUID reportId) {
        reviewReportRepository.deleteById(reportId);
    }
}
