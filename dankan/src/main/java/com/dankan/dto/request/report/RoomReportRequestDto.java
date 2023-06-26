package com.dankan.dto.request.report;

import lombok.Getter;

import java.util.UUID;

@Getter
public class RoomReportRequestDto {
    private Long postId;
    private String reportType;
}
