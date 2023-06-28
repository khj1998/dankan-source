package com.dankan.service.room;

import com.dankan.dto.request.room.RoomImageRequestDto;
import com.dankan.dto.response.room.RoomImageResponseDto;

import java.io.IOException;
import java.util.UUID;

public interface RoomService {
    RoomImageResponseDto addImages(RoomImageRequestDto roomImageRequestDto, String type) throws IOException;
    RoomImageResponseDto editImages(RoomImageRequestDto roomImageRequestDto,String type) throws IOException;
}
