package com.dankan.domain;

import com.dankan.dto.request.post.PostRoomCreateRequestDto;
import io.swagger.annotations.ApiModel;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@ApiModel(value = "매매 게시물 엔티티")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "post")
public class Post {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "post_id",nullable = false,length = 36,columnDefinition = "varchar")
    @Type(type = "uuid-char")
    private UUID postId;

    @Column(name = "room_id",nullable = false,length = 36,columnDefinition = "varchar")
    @Type(type = "uuid-char")
    private UUID roomId;

    @Column(name = "user_id",nullable = false,length = 36,columnDefinition = "varchar")
    @Type(type = "uuid-char")
    private UUID userId;

    @UpdateTimestamp
    private Date updatedAt;

    @Column(name = "title",nullable = false,length = 64,columnDefinition = "varchar")
    private String title;

    @Column(name = "content",nullable = false,columnDefinition = "varchar")
    private String content;

    public static Post of(PostRoomCreateRequestDto postRoomCreateRequestDto, UUID userId, UUID roomId) {
        return Post.builder()
                .userId(userId)
                .roomId(roomId)
                .title(postRoomCreateRequestDto.getTitle())
                .content(postRoomCreateRequestDto.getContent())
                .build();
    }
}
