package com.dankan.dto.response.review;

import com.dankan.domain.Options;
import com.dankan.domain.Room;
import com.dankan.domain.RoomReview;
import com.dankan.enum_converter.RoomTypeEnum;
import com.dankan.enum_converter.SatisfyEnum;
import lombok.*;
import org.springframework.security.core.parameters.P;

import java.util.List;
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

    public static ReviewRateResponseDto of(Room room,List<RoomReview> reviewList, String imageUrl) {
        Long reviewCount = (long) reviewList.size();
        String roomType = "";
        Double avgTotalRate = 0.0;
        Double avgCleanRate = 0.0;
        Double avgNoiseRate = 0.0;
        Double avgAccessRate = 0.0;
        Double avgHostRate = 0.0;
        Double avgFacilityRate = 0.0;

        for (Options options : room.getOptionsList()) {
            if (options.getCodeKey().contains("RoomType")) {
                roomType = RoomTypeEnum.getRoomTypeName(options.getValue());
                break;
            }
        }

        for (RoomReview roomReview : reviewList) {
            avgTotalRate += roomReview.getTotalRate();

            for (Options options : roomReview.getOptionsList()) {
                if (options.getCodeKey().contains("CleanRate")) {
                    avgCleanRate += options.getValue();
                }
                if (options.getCodeKey().contains("NoiseRate")) {
                    avgNoiseRate += options.getValue();
                }
                if (options.getCodeKey().contains("AccessRate")) {
                    avgAccessRate += options.getValue();
                }
                if (options.getCodeKey().contains("HostRate")) {
                    avgHostRate += options.getValue();
                }
                if (options.getCodeKey().contains("FacilityRate")){
                    avgFacilityRate += options.getValue();
                }
            }
        }

        if (reviewCount > 0) {
            avgTotalRate = avgTotalRate / (double) reviewCount;
            avgCleanRate = avgCleanRate / (double) reviewCount;
            avgNoiseRate = avgNoiseRate / (double) reviewCount;
            avgAccessRate = avgAccessRate / (double) reviewCount;
            avgHostRate = avgHostRate / (double) reviewCount;
            avgFacilityRate = avgFacilityRate / (double) reviewCount;
        }

        return ReviewRateResponseDto.builder()
                .address(room.getRoomAddress().getAddress())
                .reviewCount(reviewCount)
                .imageUrl(imageUrl)
                .roomType(roomType)
                .avgTotalRate(Math.round(avgTotalRate*10)/10.0)
                .avgCleanRate(Math.round(avgCleanRate*10)/10.0)
                .avgNoiseRate(Math.round(avgNoiseRate*10)/10.0)
                .avgAccessRate(Math.round(avgAccessRate*10)/10.0)
                .avgHostRate(Math.round(avgHostRate*10)/10.0)
                .avgFacilityRate(Math.round(avgFacilityRate*10)/10.0)
                .build();
    }
}
