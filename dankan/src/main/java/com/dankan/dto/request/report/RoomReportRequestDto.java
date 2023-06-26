package com.dankan.dto.request.report;

import lombok.Getter;

import java.util.UUID;

@Getter
public class RoomReportRequestDto {
    private UUID postId;
    private String reportType;
}
