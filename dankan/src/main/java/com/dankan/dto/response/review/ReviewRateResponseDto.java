package com.dankan.dto.response.review;

import com.dankan.domain.Room;
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

    public static ReviewRateResponseDto of(Room room, Long reviewCount,String imageUrl) {
        Double avgTotalRate = 0.0;
        Double avgCleanRate = 0.0;
        Double avgNoiseRate = 0.0;
        Double avgAccessRate = 0.0;
        Double avgHostRate = 0.0;
        Double avgFacilityRate = 0.0;

        if (reviewCount > 0) {
        }

        return ReviewRateResponseDto.builder()
                .address(room.getRoomAddress().getAddress())
                .reviewCount(reviewCount)
                .imageUrl(imageUrl)
                .avgTotalRate(avgTotalRate)
                .avgCleanRate(avgCleanRate)
                .avgNoiseRate(avgNoiseRate)
                .avgAccessRate(avgAccessRate)
                .avgHostRate(avgHostRate)
                .avgFacilityRate(avgFacilityRate)
                .build();
    }
}
