package com.dankan.service.room;

import com.dankan.dto.response.room.RoomImageResponseDto;
import com.dankan.dto.resquest.room.RoomImageRequestDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface RoomService {
    RoomImageResponseDto addRoomImage(List<MultipartFile> images, String type);
}