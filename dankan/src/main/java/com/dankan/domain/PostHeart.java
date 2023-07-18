package com.dankan.domain;

import io.swagger.annotations.ApiModel;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_heart_id", columnDefinition = "int")
    private Long postHeartId;

    @Column(name = "post_id",nullable = false, columnDefinition = "int")
    private Long postId;

    @Column(name = "user_id",nullable = false, columnDefinition = "bigint")
    private Long userId;

    @Column(name = "date_id", columnDefinition = "int")
    private Long dateId;

    @CreationTimestamp
    @Column(name = "created_at",columnDefinition = "datetime")
    private LocalDateTime createdAt;

    public static PostHeart of(Long postId, Long userId,Long dateId) {
        return PostHeart.builder()
                .postId(postId)
                .userId(userId)
                .dateId(dateId)
                .build();
    }
}
