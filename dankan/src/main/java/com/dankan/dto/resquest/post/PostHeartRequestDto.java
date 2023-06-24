package com.dankan.dto.resquest.post;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class PostHeartRequestDto {
    private UUID postId;
}
