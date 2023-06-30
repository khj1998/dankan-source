package com.dankan.dto.request.image;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class ImageEditRequestDto {
    private Long id;
    private String type;
    private String lastImgUrl;

    @Schema(title = "수정된 이미지 리스트",description = "이미지 수정")
    private List<MultipartFile> multipartFileList;
}
