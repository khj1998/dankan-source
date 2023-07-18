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

    private String address;
    private String roomType;
    private Double avgTotalRate;
    private Long reviewCount;
    private Double avgCleanRate;
    private Double avgNoiseRate;
    private Double avgAccessRate;
    private Double avgHostRate;
    private Double avgFacilityRate;
    private String imgUrl;

    public static ReviewRateResponseDto of(List<RoomReview> reviewList,Options options,String address,String imgUrl) {
        Long reviewCount = (long) reviewList.size();
        String roomTypeName = RoomTypeEnum.getRoomTypeName(options.getValue());

        Double avgTotalRate = 0.0;
        Double avgCleanRate = 0.0;
        Double avgNoiseRate = 0.0;
        Double avgAccessRate = 0.0;
        Double avgHostRate = 0.0;
        Double avgFacilityRate = 0.0;

        for (RoomReview roomReview : reviewList) {
            avgTotalRate += roomReview.getTotalRate();
            avgCleanRate += roomReview.getCleanRate();
            avgNoiseRate += roomReview.getNoiseRate();
            avgAccessRate += roomReview.getAccessRate();
            avgHostRate += roomReview.getHostRate();
            avgFacilityRate += roomReview.getFacilityRate();
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
                .address(address)
                .roomType(roomTypeName)
                .reviewCount(reviewCount)
                .avgTotalRate(Math.round(avgTotalRate*10)/10.0)
                .avgCleanRate(Math.round(avgCleanRate*10)/10.0)
                .avgNoiseRate(Math.round(avgNoiseRate*10)/10.0)
                .avgAccessRate(Math.round(avgAccessRate*10)/10.0)
                .avgHostRate(Math.round(avgHostRate*10)/10.0)
                .avgFacilityRate(Math.round(avgFacilityRate*10)/10.0)
                .imgUrl(imgUrl)
                .build();
    }
}
