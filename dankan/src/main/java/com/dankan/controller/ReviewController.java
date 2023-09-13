package com.dankan.controller;

import com.dankan.dto.request.image.ImageRequestDto;
import com.dankan.dto.response.image.ImageResponseDto;
import com.dankan.dto.response.review.ReviewDetailResponseDto;
import com.dankan.dto.response.review.ReviewRateResponseDto;
import com.dankan.dto.response.review.ReviewResponseDto;
import com.dankan.dto.request.review.ReviewDetailRequestDto;
import com.dankan.dto.request.review.ReviewRequestDto;
import com.dankan.dto.response.review.ReviewSearchResponse;
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

    private final ReviewService reviewService;
    private final S3UploadService s3UploadService;
    private final ImageService imageService;

    @ApiOperation("매물 후기 조회 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "매물 후기 상세 조회 성공 ")
    })
    @GetMapping("/recent")
    public ResponseEntity<List<ReviewResponseDto>> getReviews(@RequestParam("pages") Integer pages) {
        List<ReviewResponseDto> responseDtoList = reviewService.findRecentReview(pages);
        return ResponseEntity.ok(responseDtoList);
    }

    @ApiOperation("매물 후기 별점순 조회 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "매물 후기 별점순 조회 성공 ")
    })
    @GetMapping("/star")
    public ResponseEntity<List<ReviewResponseDto>> getReviewsByStar(@RequestParam("pages") Integer pages) {
        List<ReviewResponseDto> responseDtoList = reviewService.findReviewByStar(pages);
        return ResponseEntity.ok(responseDtoList);
    }

    @ApiOperation("매물 리뷰 평점 조회 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "매물 리뷰 평점 조회 API")
    })
    @GetMapping("/rate")
    public ResponseEntity<ReviewRateResponseDto> getReviewRate(@RequestParam("address") String address) {
        ReviewRateResponseDto responseDto = reviewService.findReviewRate(address);
        return ResponseEntity.ok(responseDto);
    }

    @ApiOperation("매물 상세 리뷰 조회 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "매물 상세 리뷰 조회 API")
    })
    @GetMapping("/detail")
    public ResponseEntity<List<ReviewDetailResponseDto>> getReviewDetail(@RequestParam String address,@RequestParam Integer pages) {
        List<ReviewDetailResponseDto> responseDtoList = reviewService.findReviewDetail(address,pages);
        return ResponseEntity.ok(responseDtoList);
    }

    @ApiOperation("매물 후기 등록 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "매물 후기 등록 성공 ")
    })
    @PostMapping
    public ResponseEntity<ReviewResponseDto> addReview(@RequestBody ReviewRequestDto reviewRequestDto) {
        ReviewResponseDto responseDto = reviewService.addReview(reviewRequestDto);
        return ResponseEntity.ok(responseDto);
    }

    @ApiOperation("리뷰 이미지 등록 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "리뷰 이미지 등록 성공")
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
            @ApiResponse(responseCode = "200",description = "매물 후기 삭제 성공 ")
    })
    @DeleteMapping("/remove")
    public ResponseEntity removeReview(@RequestParam("reviewId") Long reviewId) {
        reviewService.deleteReview(reviewId);
        return ResponseEntity.ok().build();
    }

    @ApiOperation("건물명으로 후기 검색 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "건물명으로 후기 검색 성공")
    })
    @GetMapping("/building")
    public ResponseEntity<List<ReviewSearchResponse>> searchByBuilding(@RequestParam("buildingName") String buildingName) {
        List<ReviewSearchResponse> responseDtoList = reviewService.findReviewByBuildingName(buildingName);
        return ResponseEntity.ok(responseDtoList);
    }

    @ApiOperation("도로명 주소로 후기 검색 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "도로명 주소로 후기 검색 성공")
    })
    @GetMapping("/address")
    public ResponseEntity<List<ReviewSearchResponse>> searchByAddress(@RequestParam("address") String address) {
        List<ReviewSearchResponse> responseDtoList = reviewService.findReviewByAddress(address);
        return ResponseEntity.ok(responseDtoList);
    }
}
