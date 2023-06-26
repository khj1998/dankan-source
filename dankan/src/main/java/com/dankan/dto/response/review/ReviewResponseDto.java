package com.dankan.dto.response.review;

import com.dankan.domain.Room;
import com.dankan.domain.RoomReview;
import com.dankan.domain.User;
import com.dankan.enum_converter.RoomTypeEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReviewResponseDto {
    private UUID reviewId;
    private Date updatedAt;
    private String nickName;
    private String univ;
    private String content;
    private Long totalRate;
    private Long cleanRate;
    private Long noiseRate;
    private Long accessRate;
    private Long hostRate;
    private Long facilityRate;
    private Date startedAt;
    private Date endAt;
    private String address;
    private String addressDetail;
    private String roomType;

    public static ReviewResponseDto of(User user, RoomReview roomReview, Room room) {
        Date currentDate = new Date();

        return ReviewResponseDto.builder()
                .reviewId(roomReview.getReviewId())
                .updatedAt(currentDate)
                .nickName(user.getNickname())
                .content(roomReview.getContent())
                .totalRate(roomReview.getRoomReviewRate().getTotalRate())
                .cleanRate(roomReview.getRoomReviewRate().getCleanRate())
                .noiseRate(roomReview.getRoomReviewRate().getNoiseRate())
                .accessRate(roomReview.getRoomReviewRate().getAccessRate())
                .hostRate(roomReview.getRoomReviewRate().getHostRate())
                .facilityRate(roomReview.getRoomReviewRate().getFacilityRate())
                .startedAt(roomReview.getResidencePeriod().getStartedAt())
                .endAt(roomReview.getResidencePeriod().getEndAt())
                .address(roomReview.getAddress())
                .addressDetail(roomReview.getAddressDetail())
                .roomType(RoomTypeEnum.getRoomTypeName(room.getRoomStructure().getRoomType()))
                .build();
    }

    public static ReviewResponseDto of(Room room,RoomReview roomReview) {
        return ReviewResponseDto.builder()
                .address(room.getRoomAddress().getAddress())
                .roomType(RoomTypeEnum.getRoomTypeName(room.getRoomStructure().getRoomType()))
                .totalRate(roomReview.getRoomReviewRate().getTotalRate())
                .build();
    }
}
