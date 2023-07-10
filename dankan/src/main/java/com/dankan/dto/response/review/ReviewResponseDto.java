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
    private LocalDate updatedAt;
    private String nickName;
    private String univ;
    private String content;
    private Long totalRate;
    private Long cleanRate;
    private Long noiseRate;
    private Long accessRate;
    private Long hostRate;
    private Long facilityRate;
    private LocalDate startedAt;
    private LocalDate endAt;
    private String address;
    private String addressDetail;
    private String roomType;
    private String imgUrl;

    public static ReviewResponseDto of(User user,RoomReview roomReview) {

        return ReviewResponseDto.builder()
                .reviewId(roomReview.getReviewId())
                .updatedAt(LocalDate.now())
                .totalRate(roomReview.getTotalRate())
                .cleanRate(roomReview.getCleanRate())
                .noiseRate(roomReview.getNoiseRate())
                .accessRate(roomReview.getAccessRate())
                .hostRate(roomReview.getHostRate())
                .facilityRate(roomReview.getFacilityRate())
                .nickName(user.getNickname())
                .content(roomReview.getContent())
                .startedAt(roomReview.getResidencePeriod().getStartedAt())
                .endAt(roomReview.getResidencePeriod().getEndAt())
                .address(roomReview.getAddress())
                .addressDetail(roomReview.getAddressDetail())
                .build();
    }

    public static ReviewResponseDto of(User user, RoomReview roomReview, String imgUrls) {

        return ReviewResponseDto.builder()
                .reviewId(roomReview.getReviewId())
                .updatedAt(LocalDate.now())
                .totalRate(roomReview.getTotalRate())
                .nickName(user.getNickname())
                .content(roomReview.getContent())
                .startedAt(roomReview.getResidencePeriod().getStartedAt())
                .endAt(roomReview.getResidencePeriod().getEndAt())
                .address(roomReview.getAddress())
                .addressDetail(roomReview.getAddressDetail())
                .imgUrl(imgUrls)
                .build();
    }

    public static ReviewResponseDto of(RoomReview roomReview, String imgUrls) {

        return ReviewResponseDto.builder()
                .reviewId(roomReview.getReviewId())
                .updatedAt(LocalDate.now())
                .content(roomReview.getContent())
                .startedAt(roomReview.getResidencePeriod().getStartedAt())
                .endAt(roomReview.getResidencePeriod().getEndAt())
                .address(roomReview.getAddress())
                .addressDetail(roomReview.getAddressDetail())
                .imgUrl(imgUrls)
                .build();
    }
}
