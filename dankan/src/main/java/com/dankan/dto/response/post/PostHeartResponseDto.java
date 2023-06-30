package com.dankan.dto.response.post;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostHeartResponseDto {
    private Long postId;
    private Long userId;

    public static PostHeartResponseDto of(Long postId, Long userId) {
        return PostHeartResponseDto.builder()
                .postId(postId)
                .userId(userId)
                .build();
    }
}
