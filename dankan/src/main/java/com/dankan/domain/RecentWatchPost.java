package com.dankan.domain;

import io.swagger.annotations.ApiModel;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
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
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "recent_watch_id",nullable = false,length = 36,columnDefinition = "varchar")
    @Type(type = "uuid-char")
    private UUID recentWatchId;

    @Column(name = "user_id",nullable = false,length = 36,columnDefinition = "varchar")
    @Type(type = "uuid-char")
    private UUID userId;

    @Column(name = "post_id",nullable = false,length = 36,columnDefinition = "varchar")
    @Type(type = "uuid-char")
    private UUID postId;

    @CreationTimestamp
    private Date createdAt;

    public static RecentWatchPost of(UUID userId, UUID postId) {
        return RecentWatchPost.builder()
                .userId(userId)
                .postId(postId)
                .build();
    }
}
