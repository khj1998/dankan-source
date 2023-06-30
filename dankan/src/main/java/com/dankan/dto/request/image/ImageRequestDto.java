package com.dankan.dto.request.image;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class ImageRequestDto {
    private Long id;
    private String type;

    @Schema(title = "이미지 업로드 리스트",description = "이미지 업로드")
    private List<MultipartFile> multipartFileList;
}
