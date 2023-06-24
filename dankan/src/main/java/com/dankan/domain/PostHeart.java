package com.dankan.domain;

import io.swagger.annotations.ApiModel;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@ApiModel(value = "찜한 게시물 엔티티")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "post_heart")
public class PostHeart {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "post_heart_id",nullable = false,length = 36,columnDefinition = "varchar")
    @Type(type = "uuid-char")
    private UUID postHeartId;

    @Column(name = "post_id",nullable = false,length = 36,columnDefinition = "varchar")
    @Type(type = "uuid-char")
    private UUID postId;

    @Column(name = "user_id",nullable = false,length = 36,columnDefinition = "varchar")
    @Type(type = "uuid-char")
    private UUID userId;

    public static PostHeart of(UUID postId, UUID userId) {
        return PostHeart.builder()
                .postId(postId)
                .userId(userId)
                .build();
    }
}
