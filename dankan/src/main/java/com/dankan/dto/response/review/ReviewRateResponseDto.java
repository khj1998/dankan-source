package com.dankan.dto.response.review;

import com.dankan.domain.Room;
import com.dankan.domain.embedded.RoomReviewRate;
import com.dankan.enum_converter.RoomTypeEnum;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewRateResponseDto {
    private String imageUrl;
    private String address;
    private String roomType;
    private Double avgTotalRate;
    private Long reviewCount;
    private Double avgCleanRate;
    private Double avgNoiseRate;
    private Double avgAccessRate;
    private Double avgHostRate;
    private Double avgFacilityRate;

    public static ReviewRateResponseDto of(RoomReviewRate roomReviewRate, Room room, Long reviewCount) {
        Double avgTotalRate;
        Double avgCleanRate;
        Double avgNoiseRate;
        Double avgAccessRate;
        Double avgHostRate;
        Double avgFacilityRate;

        if (reviewCount > 0) {
            avgTotalRate = (roomReviewRate.getTotalRate())/(double)reviewCount;
            avgCleanRate = (roomReviewRate.getCleanRate())/(double)reviewCount;
            avgNoiseRate = (roomReviewRate.getNoiseRate())/(double)reviewCount;
            avgAccessRate = (roomReviewRate.getAccessRate())/(double)reviewCount;
            avgHostRate = (roomReviewRate.getHostRate())/(double)reviewCount;
            avgFacilityRate = (roomReviewRate.getFacilityRate())/(double)reviewCount;
        } else {
            avgTotalRate = 0.0;
            avgCleanRate = 0.0;
            avgNoiseRate = 0.0;
            avgAccessRate = 0.0;
            avgHostRate = 0.0;
            avgFacilityRate = 0.0;
        }

        return ReviewRateResponseDto.builder()
                .address(room.getRoomAddress().getAddress())
                .roomType(RoomTypeEnum.getRoomTypeName(room.getRoomStructure().getRoomType()))
                .reviewCount(reviewCount)
                .avgTotalRate(avgTotalRate)
                .avgCleanRate(avgCleanRate)
                .avgNoiseRate(avgNoiseRate)
                .avgAccessRate(avgAccessRate)
                .avgHostRate(avgHostRate)
                .avgFacilityRate(avgFacilityRate)
                .build();
    }
}
