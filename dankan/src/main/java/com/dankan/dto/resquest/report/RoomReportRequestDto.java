package com.dankan.dto.resquest.report;

import lombok.Getter;

import java.util.UUID;

@Getter
public class RoomReportRequestDto {
    private UUID postId;
    private String reportType;
}
