package com.dankan.service.report;

import com.dankan.domain.*;
import com.dankan.dto.response.report.ReviewReportResponseDto;
import com.dankan.dto.response.report.RoomReportResponseDto;
import com.dankan.dto.request.report.ReviewReportRequestDto;
import com.dankan.dto.request.report.RoomReportRequestDto;
import com.dankan.exception.post.PostNotFoundException;
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
    public RoomReportResponseDto addPostReport(RoomReportRequestDto roomReportRequestDto) {
        Long userId = JwtUtil.getMemberId();
        Post post = postRepository.findById(roomReportRequestDto.getPostId())
                .orElseThrow(() -> new PostNotFoundException(roomReportRequestDto.getPostId()));

        Long roomId = post.getRoomId();

        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RoomNotFoundException(roomId));

        PostReport postReport = PostReport.of(room, userId);
        postReportRepository.save(postReport);

        return RoomReportResponseDto.of(true);
    }

    @Override
    @Transactional
    public ReviewReportResponseDto addReviewReport(ReviewReportRequestDto reviewReportRequestDto) {
        Long userId = JwtUtil.getMemberId();
        RoomReview roomReview = reviewRepository.findById(reviewReportRequestDto.getReviewId())
                .orElseThrow(() -> new ReviewNotFoundException(reviewReportRequestDto.getReviewId()));

        ReviewReport reviewReport = ReviewReport.of(userId, roomReview);
        reviewReportRepository.save(reviewReport);

        return ReviewReportResponseDto.of(true);
    }
}
