package com.dankan.service.review;


import com.dankan.dto.request.image.ImageRequestDto;
import com.dankan.dto.response.image.ImageResponseDto;
import com.dankan.dto.response.review.ReviewDetailResponseDto;
import com.dankan.dto.response.review.ReviewImageResponseDto;
import com.dankan.dto.response.review.ReviewRateResponseDto;
import com.dankan.dto.response.review.ReviewResponseDto;
import com.dankan.dto.request.review.ReviewDetailRequestDto;
import com.dankan.dto.request.review.ReviewRequestDto;

import java.util.List;
import java.util.UUID;

public interface ReviewService {
    ReviewResponseDto addReview(ReviewRequestDto reviewRequestDto);
    List<ReviewDetailResponseDto> findReviewDetail(ReviewDetailRequestDto reviewDetailRequestDto);
    ReviewRateResponseDto findReviewRate(String address);
    List<ReviewResponseDto> findRecentReview(Integer pages);
    List<ReviewResponseDto> findReviewByStar(Integer pages);
    void deleteReview(Long reviewId);
}
