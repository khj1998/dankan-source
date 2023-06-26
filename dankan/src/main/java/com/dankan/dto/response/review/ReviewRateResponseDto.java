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

    public static ReviewRateResponseDto of(Room room, Long reviewCount) {
        Double avgTotalRate;
        Double avgCleanRate;
        Double avgNoiseRate;
        Double avgAccessRate;
        Double avgHostRate;
        Double avgFacilityRate;

        if (reviewCount > 0) {
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
                .reviewCount(reviewCount)
                .build();
    }
}
