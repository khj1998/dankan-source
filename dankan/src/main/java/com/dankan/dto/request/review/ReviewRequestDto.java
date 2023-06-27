package com.dankan.dto.request.review;

import lombok.Getter;

import java.time.LocalDate;
import java.util.Date;

@Getter
public class ReviewRequestDto {
    private String content;
    private Long totalRate;
    private String cleanRate;
    private String noiseRate;
    private String accessRate;
    private String hostRate;
    private String facilityRate;
    private LocalDate startedAt;
    private LocalDate endAt;
    private String address;
    private String addressDetail;
}
