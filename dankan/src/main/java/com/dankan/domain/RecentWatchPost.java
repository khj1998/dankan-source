package com.dankan.domain;

import io.swagger.annotations.ApiModel;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@ApiModel(value = "최근 본 게시물 엔티티")
@Getter
@Setter
@Entity
@Table(name = "recent_watch_post")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecentWatchPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recent_watch_id",columnDefinition = "int")
    private Long recentWatchId;

    @Column(name = "user_id",nullable = false,columnDefinition = "int")
    private Long userId;

    @Column(name = "post_id",nullable = false,columnDefinition = "int")
    private Long postId;

    @CreationTimestamp
    private LocalDate updatedAt;

    public static RecentWatchPost of(Long userId, Long postId) {
        return RecentWatchPost.builder()
                .userId(userId)
                .postId(postId)
                .build();
    }
}
