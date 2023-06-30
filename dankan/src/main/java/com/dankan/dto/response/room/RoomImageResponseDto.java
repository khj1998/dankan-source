package com.dankan.dto.response.room;

import lombok.*;

@Getter
@Setter
@Builder
public class RoomImageResponseDto {
    private Long roomId;
    private String imgUrls;
}
