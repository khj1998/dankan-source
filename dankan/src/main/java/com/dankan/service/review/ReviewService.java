package com.dankan.service.review;


import com.dankan.dto.response.review.ReviewDetailResponseDto;
import com.dankan.dto.response.review.ReviewRateResponseDto;
import com.dankan.dto.response.review.ReviewResponseDto;
import com.dankan.dto.resquest.review.ReviewDetailRequestDto;
import com.dankan.dto.resquest.review.ReviewRequestDto;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface ReviewService {
    ReviewResponseDto addReview(ReviewRequestDto reviewRequestDto);
    List<ReviewDetailResponseDto> findReviewDetail(ReviewDetailRequestDto reviewDetailRequestDto);
    ReviewRateResponseDto findReviewRate(String address);
    List<ReviewResponseDto> findRecentReview(Integer pages);
    List<ReviewResponseDto> findReviewByStar(Integer pages);

    void deleteReview(UUID reviewId);
}
