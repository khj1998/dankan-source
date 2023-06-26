package com.dankan.dto.response.room;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class RoomImageResponseDto {
    private Long roomId;
    private List<String> image;
}
