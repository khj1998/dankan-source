package com.dankan.dto.response.post;

import com.dankan.domain.Post;
import lombok.*;

import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostEditResponseDto {
    private Long postId;
    private String title;
    private String content;

    public static PostEditResponseDto of(Post post) {
        return PostEditResponseDto.builder()
                .postId(post.getPostId())
                .title(post.getTitle())
                .content(post.getContent())
                .build();
    }
}
