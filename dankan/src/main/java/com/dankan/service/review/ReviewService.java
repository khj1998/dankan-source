package com.dankan.service.review;


import com.dankan.dto.request.image.ImageRequestDto;
import com.dankan.dto.response.image.ImageResponseDto;
import com.dankan.dto.response.review.*;
import com.dankan.dto.request.review.ReviewDetailRequestDto;
import com.dankan.dto.request.review.ReviewRequestDto;

import java.util.List;
import java.util.UUID;

public interface ReviewService {
    ReviewResponseDto addReview(ReviewRequestDto reviewRequestDto);
    List<ReviewDetailResponseDto> findReviewDetail(String address,Integer pages);
    ReviewRateResponseDto findReviewRate(String address);
    List<ReviewResponseDto> findRecentReview(Integer pages);
    List<ReviewResponseDto> findReviewByStar(Integer pages);
    List<ReviewSearchResponse> findReviewByAddress(String address);
    void deleteReview(Long reviewId);
}
