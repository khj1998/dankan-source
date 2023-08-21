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
    private Double totalRate;
    private Long cleanRate;
    private Long noiseRate;
    private Long accessRate;
    private Long hostRate;
    private Long facilityRate;
    private LocalDate startedAt;
    private LocalDate endAt;
    private String buildingName;
    private String address;
    private String imgUrl;

    public static ReviewResponseDto of(User user,RoomReview roomReview) {
        String[] addressInfo = roomReview.getAddress().split(" ");

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
                .buildingName(addressInfo[4])
                .build();
    }

    public static ReviewResponseDto of(User user, RoomReview roomReview, String imgUrls) {
       String[] addressInfo = roomReview.getAddress().split(" ");

        return ReviewResponseDto.builder()
                .reviewId(roomReview.getReviewId())
                .updatedAt(LocalDate.now())
                .totalRate(roomReview.getTotalRate())
                .nickName(user.getNickname())
                .content(roomReview.getContent())
                .startedAt(roomReview.getResidencePeriod().getStartedAt())
                .endAt(roomReview.getResidencePeriod().getEndAt())
                .buildingName(addressInfo[4])
                .imgUrl(imgUrls)
                .build();
    }

    public static ReviewResponseDto of(RoomReview roomReview,String imgUrls) {
        String[] addressInfo = roomReview.getAddress().split(" ");

        return ReviewResponseDto.builder()
                .reviewId(roomReview.getReviewId())
                .totalRate(roomReview.getTotalRate())
                .address(roomReview.getAddress())
                .updatedAt(LocalDate.now())
                .content(roomReview.getContent())
                .buildingName(addressInfo[4])
                .imgUrl(imgUrls)
                .build();
    }
}
