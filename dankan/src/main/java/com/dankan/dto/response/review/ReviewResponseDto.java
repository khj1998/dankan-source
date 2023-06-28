package com.dankan.dto.response.review;

import com.dankan.domain.Room;
import com.dankan.domain.RoomReview;
import com.dankan.domain.User;
import com.dankan.enum_converter.RoomTypeEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;
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

    public static ReviewResponseDto of(User user, RoomReview roomReview,String imageUrl) {

        return ReviewResponseDto.builder()
                .reviewId(roomReview.getReviewId())
                .updatedAt(LocalDate.now())
                .nickName(user.getNickname())
                .content(roomReview.getContent())
                .startedAt(roomReview.getResidencePeriod().getStartedAt())
                .endAt(roomReview.getResidencePeriod().getEndAt())
                .address(roomReview.getAddress())
                .addressDetail(roomReview.getAddressDetail())
                .imgUrl(imageUrl)
                .build();
    }

    public static ReviewResponseDto of(Room room,RoomReview roomReview,String imgUrl) {
        return ReviewResponseDto.builder()
                .address(room.getRoomAddress().getAddress())
                //.roomType(RoomTypeEnum.getRoomTypeName(room.getRoomStructure().getRoomType()))
                //.totalRate(roomReview.getRoomReviewRate().getTotalRate())
                .imgUrl(imgUrl)
                .build();
    }
}
