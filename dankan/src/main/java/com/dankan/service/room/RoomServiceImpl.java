package com.dankan.service.room;

import com.dankan.dto.response.room.RoomImageResponseDto;
import com.dankan.repository.RoomRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@Transactional
public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepository;

    public RoomServiceImpl(final RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    @Transactional
    public RoomImageResponseDto addRoomImage(List<MultipartFile> images, String type) {
        return null;
    }
}
