package com.dankan.controller;

import com.dankan.dto.request.image.ImageRequestDto;
import com.dankan.dto.response.image.ImageResponseDto;
import com.dankan.dto.response.review.ReviewDetailResponseDto;
import com.dankan.dto.response.review.ReviewRateResponseDto;
import com.dankan.dto.response.review.ReviewResponseDto;
import com.dankan.dto.request.review.ReviewDetailRequestDto;
import com.dankan.dto.request.review.ReviewRequestDto;
import com.dankan.repository.ImageRepository;
import com.dankan.service.image.ImageService;
import com.dankan.service.review.ReviewService;
import com.dankan.service.s3.S3UploadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/review")
@Api(tags = {"후기 관련 api"})
@RequiredArgsConstructor
public class ReviewController {
    private final ImageRepository imageRepository;
    /**
     * TODO: 후기 생성 API
     * TODO: 후기 삭제 API
     * TODO: 후기 최신순 정렬 API
     * TODO: 해당 매물의 후기 확인 API
     * TODO: 후기 신고 API
     * */

    private final ReviewService reviewService;
    private final S3UploadService s3UploadService;
    private final ImageService imageService;

    @ApiOperation("매물 후기 조회 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "매물 후기 상세 조회 성공 "),
            @ApiResponse(responseCode = "401",description = "인증되지 않은 사용자"),
            @ApiResponse(responseCode = "403",description = "유저가 Member | Admin 권한이 없음"),
            @ApiResponse(responseCode = "404",description = "매물 후기 상세 조회에 실패함")
    })
    @GetMapping("/recent")
    public ResponseEntity<List<ReviewResponseDto>> getReviews(@RequestParam("pages") Integer pages) {
        List<ReviewResponseDto> responseDtoList = reviewService.findRecentReview(pages);
        return ResponseEntity.ok(responseDtoList);
    }

    @ApiOperation("매물 후기 별점순 조회 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "매물 후기 별점순 조회 성공 "),
            @ApiResponse(responseCode = "401",description = "인증되지 않은 사용자"),
            @ApiResponse(responseCode = "403",description = "유저가 Member | Admin 권한이 없음"),
            @ApiResponse(responseCode = "404",description = "매물 후기 별점순 조회에 실패함")
    })
    @GetMapping("/star")
    public ResponseEntity<List<ReviewResponseDto>> getReviewsByStar(@RequestParam("pages") Integer pages) {
        List<ReviewResponseDto> responseDtoList = reviewService.findReviewByStar(pages);
        return ResponseEntity.ok(responseDtoList);
    }

    @ApiOperation("매물 리뷰 평점 조회 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "매물 리뷰 평점 조회 API"),
            @ApiResponse(responseCode = "401",description = "인증되지 않은 사용자"),
            @ApiResponse(responseCode = "403",description = "유저가 Member | Admin 권한이 없음"),
            @ApiResponse(responseCode = "404",description = "매물 리뷰 평점 조회에 실패함")
    })
    @GetMapping("/rate")
    public ResponseEntity<ReviewRateResponseDto> getReviewRate(@RequestParam("address") String address) {
        ReviewRateResponseDto responseDto = reviewService.findReviewRate(address);
        return ResponseEntity.ok(responseDto);
    }

    @ApiOperation("매물 상세 리뷰 조회 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "매물 상세 리뷰 조회 API"),
            @ApiResponse(responseCode = "401",description = "인증되지 않은 사용자"),
            @ApiResponse(responseCode = "403",description = "유저가 Member | Admin 권한이 없음"),
            @ApiResponse(responseCode = "404",description = "매물 상세 리뷰 조회에 실패함")
    })
    @PostMapping("/detail")
    public ResponseEntity<List<ReviewDetailResponseDto>> getReviewDetail(@RequestBody ReviewDetailRequestDto reviewDetailRequestDto) {
        List<ReviewDetailResponseDto> responseDtoList = reviewService.findReviewDetail(reviewDetailRequestDto);
        return ResponseEntity.ok(responseDtoList);
    }

    @ApiOperation("매물 후기 등록 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "매물 후기 등록 성공 "),
            @ApiResponse(responseCode = "401",description = "인증되지 않은 사용자"),
            @ApiResponse(responseCode = "403",description = "유저가 Member | Admin 권한이 없음"),
            @ApiResponse(responseCode = "404",description = "매물 후기 등록에 실패함")
    })
    @PostMapping
    public ResponseEntity<ReviewResponseDto> addReview(@RequestBody ReviewRequestDto reviewRequestDto) {
        ReviewResponseDto responseDto = reviewService.addReview(reviewRequestDto);
        return ResponseEntity.ok(responseDto);
    }

    @ApiOperation("리뷰 이미지 등록 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "리뷰 이미지 등록 성공"),
            @ApiResponse(responseCode = "401",description = "인증되지 않은 사용자"),
            @ApiResponse(responseCode = "403",description = "유저가 Member | Admin 권한이 없음"),
            @ApiResponse(responseCode = "404",description = "리뷰 이미지 등록 실패")
    })
    @PostMapping(value = "/image",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ImageResponseDto> addReviewImage(@ModelAttribute ImageRequestDto imageRequestDto) throws IOException {
        String imgUrl = "";

        for (MultipartFile multipartFile : imageRequestDto.getMultipartFileList()) {
            imgUrl += s3UploadService.upload(multipartFile, "room-image") + " ";
        }

        ImageResponseDto responseDto = imageService.addReviewImages(imageRequestDto,imgUrl);

        return ResponseEntity.ok(responseDto);
    }

    @ApiOperation("매물 후기 삭제 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "매물 후기 삭제 성공 "),
            @ApiResponse(responseCode = "401",description = "인증되지 않은 사용자"),
            @ApiResponse(responseCode = "403",description = "유저가 Member | Admin 권한이 없음"),
            @ApiResponse(responseCode = "404",description = "매물 후기 삭제에 실패함")
    })
    @DeleteMapping("/remove")
    public ResponseEntity removeReview(@RequestParam("reviewId") Long reviewId) {
        reviewService.deleteReview(reviewId);
        return ResponseEntity.ok().build();
    }
}
