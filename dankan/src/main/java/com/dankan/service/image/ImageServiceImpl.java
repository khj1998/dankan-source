package com.dankan.service.image;

import com.dankan.domain.Image;
import com.dankan.domain.Post;
import com.dankan.domain.Room;
import com.dankan.dto.request.image.ImageEditRequestDto;
import com.dankan.dto.request.image.ImageRequestDto;
import com.dankan.dto.response.image.ImageResponseDto;
import com.dankan.enum_converter.ImageTypeEnum;
import com.dankan.exception.image.ImageNotFoundException;
import com.dankan.exception.post.PostNotFoundException;
import com.dankan.repository.ImageRepository;
import com.dankan.repository.PostRepository;
import com.dankan.repository.RoomRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ImageServiceImpl implements ImageService {
    private final PostRepository postRepository;
    private final ImageRepository imageRepository;

    public ImageServiceImpl(final PostRepository postRepository, final ImageRepository imageRepository) {
        this.postRepository = postRepository;
        this.imageRepository = imageRepository;
    }

    @Override
    @Transactional
    public ImageResponseDto addRoomImages(ImageRequestDto imageRequestDto, String imgUrls) {
        String[] imgUrlList = imgUrls.split(" ");
        Post post = postRepository.findById(imageRequestDto.getId())
                .orElseThrow(() -> new PostNotFoundException(imageRequestDto.getId()));

        for (String s : imgUrlList) {
            Image image = Image.of(imageRequestDto.getType(), s, post.getRoomId());
            imageRepository.save(image);
        }

        return ImageResponseDto.builder()
                .imgUrls(imgUrls)
                .build();
    }

    @Override
    @Transactional
    public ImageResponseDto addReviewImages(ImageRequestDto imageRequestDto, String imgUrls)  {
        String[] imgUrlList = imgUrls.split(" ");

        for (String s : imgUrlList) {
            Image image = Image.of(imageRequestDto.getType(), s, imageRequestDto.getId());
            imageRepository.save(image);
        }

        return ImageResponseDto.builder()
                .imgUrls(imgUrls)
                .build();
    }

    @Override
    @Transactional
    public ImageResponseDto editRoomImages(ImageEditRequestDto imageEditRequestDto, String imgUrls) {
        Post post = postRepository.findById(imageEditRequestDto.getId())
                .orElseThrow(() -> new PostNotFoundException(imageEditRequestDto.getId()));

        Long imageType = ImageTypeEnum.getRoomImageTypeValue(imageEditRequestDto.getType());
        String[] lastImgUrls = imageEditRequestDto.getLastImgUrl().split(" ");
        String[] newImgUrls = imgUrls.split(" ");
        List<Image> imageList = new ArrayList<>();

        log.info("리스트 크기 확인 {},{}",lastImgUrls.length,newImgUrls.length);

        for (String lastImgUrl : lastImgUrls) {
            Image image = imageRepository.findEditImage(post.getRoomId(), lastImgUrl,imageType)
                    .orElseThrow(() -> new ImageNotFoundException(post.getRoomId()));
            imageList.add(image);
        }

        if (imageList.size() == 0) {
            throw new ImageNotFoundException(imageEditRequestDto.getId());
        }

        for (int i=0;i<imageList.size();i++) {
            Image image = imageList.get(i);
            image.setImageUrl(newImgUrls[i]);
            imageRepository.save(image);
        }

        return ImageResponseDto.builder()
                .imgUrls(imgUrls)
                .build();
    }
}
