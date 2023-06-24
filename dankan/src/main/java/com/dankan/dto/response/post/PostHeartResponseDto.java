package com.dankan.dto.response.post;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostHeartResponseDto {
    private UUID postId;
    private UUID userId;

    public static PostHeartResponseDto of(UUID postId, UUID userId) {
        return PostHeartResponseDto.builder()
                .postId(postId)
                .userId(userId)
                .build();
    }
}
