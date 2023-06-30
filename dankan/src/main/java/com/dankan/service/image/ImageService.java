package com.dankan.service.image;

import com.dankan.dto.request.image.ImageEditRequestDto;
import com.dankan.dto.request.image.ImageRequestDto;
import com.dankan.dto.response.image.ImageResponseDto;

import java.io.IOException;

public interface ImageService {
    ImageResponseDto addRoomImages(ImageRequestDto imageRequestDto, String imgUrls) throws IOException;
    ImageResponseDto addReviewImages(ImageRequestDto imageRequestDto, String imgUrls) throws IOException;
    ImageResponseDto editRoomImages(ImageEditRequestDto imageEditRequestDto, String imgUrls) throws IOException;
}
