package com.dankan.domain.embedded;

import com.dankan.domain.RoomReview;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class RoomReviewRate {

    @Column(name = "total_rate",nullable = false,columnDefinition = "int")
    private Long totalRate;

    @Column(name = "clean_rate",nullable = false,columnDefinition = "int")
    private Long cleanRate;

    @Column(name = "noise_rate",nullable = false,columnDefinition = "int")
    private Long noiseRate;

    @Column(name = "access_rate",nullable = false,columnDefinition = "int")
    private Long accessRate;

    @Column(name = "host_rate",nullable = false,columnDefinition = "int")
    private Long hostRate;

    @Column(name = "facility_rate",nullable = false,columnDefinition = "int")
    private Long facilityRate;

    public static RoomReviewRate init() {
        return RoomReviewRate.builder()
                .totalRate(0L)
                .cleanRate(0L)
                .noiseRate(0L)
                .accessRate(0L)
                .hostRate(0L)
                .facilityRate(0L)
                .build();
    }

    public void plusRate(RoomReview roomReview) {
        this.totalRate += roomReview.getRoomReviewRate().getTotalRate();
        this.cleanRate += roomReview.getRoomReviewRate().getCleanRate();
        this.noiseRate += roomReview.getRoomReviewRate().getNoiseRate();
        this.accessRate += roomReview.getRoomReviewRate().getAccessRate();
        this.hostRate += roomReview.getRoomReviewRate().getHostRate();
        this.facilityRate += roomReview.getRoomReviewRate().getFacilityRate();
    }
}
