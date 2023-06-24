package com.dankan.dto.resquest.review;

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
    private Date startedAt;
    private Date endAt;
    private String address;
    private String addressDetail;
}
