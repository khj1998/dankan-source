package com.dankan.service.room;

import com.dankan.domain.RoomImage;
import com.dankan.dto.request.room.RoomImageRequestDto;
import com.dankan.dto.response.room.RoomImageResponseDto;
import com.dankan.exception.room.RoomImageNotFoundException;
import com.dankan.repository.RoomImageRepository;
import com.dankan.service.s3.S3UploadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Slf4j
public class RoomServiceImpl implements RoomService {

    private final RoomImageRepository roomImageRepository;

    public RoomServiceImpl(RoomImageRepository roomImageRepository) {
        this.roomImageRepository = roomImageRepository;
    }

    @Override
    @Transactional
    public RoomImageResponseDto addImages(RoomImageRequestDto roomImageRequestDto,String imgUrl)  {
        RoomImage roomImage;

        roomImage = RoomImage.of(roomImageRequestDto.getType(), imgUrl,roomImageRequestDto.getRoomId());
        roomImageRepository.save(roomImage);

        return RoomImageResponseDto.builder()
                .imgUrls(imgUrl)
                .build();
    }

    @Override
    @Transactional
    public RoomImageResponseDto editImages(RoomImageRequestDto roomImageRequestDto,String imgUrl) {
        Long imgType;

        if (roomImageRequestDto.getType().equals("대표")) {
            imgType = 0L;
        } else if (roomImageRequestDto.getType().equals("거실/방")) {
            imgType = 1L;
        } else {
            imgType = 2L;
        }

        RoomImage roomImage = roomImageRepository.findByRoomIdAndImageType(roomImageRequestDto.getRoomId(),imgType)
                .orElseThrow(() -> new RoomImageNotFoundException(roomImageRequestDto.getRoomId()));

        roomImage.setRoomImageUrl(imgUrl);

        return RoomImageResponseDto.builder()
                .imgUrls(imgUrl)
                .build();
    }
}
