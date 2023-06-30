package com.dankan.dto.request.post;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class PostRoomEditRequestDto {
    private Long postId;
    private String title;
    private String content;
}
