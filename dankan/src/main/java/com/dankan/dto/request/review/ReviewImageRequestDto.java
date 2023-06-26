package com.dankan.dto.request.review;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class ReviewImageRequestDto {
    private UUID reviewId;

    @Schema(title = "이미지 업로드 리스트",description = "리뷰 이미지 업로드")
    private List<MultipartFile> multipartFileList;
}
