package com.dankan.domain;

import com.dankan.dto.request.room.RoomImageRequestDto;
import io.swagger.annotations.ApiModel;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@ApiModel(value = "매물 이미지")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "room_image")
public class RoomImage {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "room_image_id",nullable = false,length = 36,columnDefinition = "varchar")
    @Type(type = "uuid-char")
    private UUID roomImageId;

    @Column(name = "room_id",nullable = false,length = 36,columnDefinition = "varchar")
    @Type(type = "uuid-char")
    private UUID roomId;

    @Column(name = "image_type",nullable = false,columnDefinition = "int")
    private Long imageType;

    @Column(name = "room_image_url",nullable = false,columnDefinition = "text")
    private String roomImageUrl;

    public static RoomImage of(String imageType,String imageUrl,UUID roomId) {
        Long imageTypeNum;

        if (imageType.equals("대표")) {
            imageTypeNum = 0L;
        } else if (imageType.equals("거실/방")) {
            imageTypeNum = 1L;
        } else {
            imageTypeNum = 2L;
        }

        return RoomImage.builder()
                .roomId(roomId)
                .imageType(imageTypeNum)
                .roomImageUrl(imageUrl)
                .build();
    }
}
