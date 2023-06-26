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
    private final RoomImageRepository roomImageRepository;

    public RoomServiceImpl(final RoomRepository roomRepository,
                           final RoomImageRepository roomImageRepository) {
        this.roomRepository = roomRepository;
        this.roomImageRepository = roomImageRepository;
    }

    @Override
    @Transactional
    public RoomImageResponseDto addRoomImage(List<MultipartFile> images, String type) {
        return null;
    }
}
