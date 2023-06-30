package com.dankan.domain;

import com.dankan.dto.request.post.PostRoomRequestDto;
import io.swagger.annotations.ApiModel;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id", columnDefinition = "int")
    private Long postId;

    @Column(name = "date_id",nullable = false, columnDefinition = "int")
    private Long dateId;

    @Column(name = "room_id",nullable = false, columnDefinition = "int")
    private Long roomId;

    @Column(name = "user_id",nullable = false,columnDefinition = "bigint")
    private Long userId;

    @UpdateTimestamp
    private LocalDate updatedAt;

    @Column(name = "title",nullable = false,length = 64,columnDefinition = "varchar")
    private String title;

    @Column(name = "content",nullable = false,columnDefinition = "varchar")
    private String content;

    public static Post of(PostRoomRequestDto postRoomRequestDto,Long userId, Long roomId,Long dateId) {
        return Post.builder()
                .dateId(dateId)
                .userId(userId)
                .roomId(roomId)
                .title(postRoomRequestDto.getTitle())
                .content(postRoomRequestDto.getContent())
                .build();
    }
}
