package com.dankan.dto.response.review;

import com.dankan.domain.*;
import com.dankan.enum_converter.RoomTypeEnum;
import com.dankan.enum_converter.SatisfyEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReviewResponseDto {
    private Long reviewId;
    private LocalDate createdAt;
    private String nickName;
    private String univ;
    private String content;
    private Double totalRate;
    private LocalDate startedAt;
    private LocalDate endAt;
    private String address;
    private String imgUrl;

    public static ReviewResponseDto of(User user,RoomReview roomReview) {
        return ReviewResponseDto.builder()
                .reviewId(roomReview.getReviewId())
                .createdAt(roomReview.getCreatedAt())
                .totalRate(roomReview.getTotalRate())
                .nickName(user.getNickname())
                .content(roomReview.getContent())
                .startedAt(roomReview.getResidencePeriod().getStartedAt())
                .endAt(roomReview.getResidencePeriod().getEndAt())
                .address(roomReview.getAddress())
                .build();
    }

    public static ReviewResponseDto of(User user, RoomReview roomReview, String imgUrls) {
        return ReviewResponseDto.builder()
                .reviewId(roomReview.getReviewId())
                .createdAt(roomReview.getCreatedAt())
                .totalRate(roomReview.getTotalRate())
                .nickName(user.getNickname())
                .content(roomReview.getContent())
                .startedAt(roomReview.getResidencePeriod().getStartedAt())
                .endAt(roomReview.getResidencePeriod().getEndAt())
                .address(roomReview.getAddress())
                .imgUrl(imgUrls)
                .build();
    }

    public static ReviewResponseDto of(RoomReview roomReview,String imgUrls) {
        return ReviewResponseDto.builder()
                .reviewId(roomReview.getReviewId())
                .totalRate(roomReview.getTotalRate())
                .address(roomReview.getAddress())
                .createdAt(roomReview.getCreatedAt())
                .content(roomReview.getContent())
                .address(roomReview.getAddress())
                .imgUrl(imgUrls)
                .build();
    }
}
