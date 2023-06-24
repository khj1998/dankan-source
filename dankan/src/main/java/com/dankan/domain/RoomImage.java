package com.dankan.domain;

import com.dankan.dto.resquest.room.RoomImageRequestDto;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@ApiModel(value = "매물 이미지")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "room_image")
public class RoomImage {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "room_id",nullable = false,length = 36,columnDefinition = "varchar")
    @Type(type = "uuid-char")
    private UUID room_id;

    @Column(name = "image_type",nullable = false,columnDefinition = "int")
    private Long imageType;

    @Column(name = "room_iamge_url",nullable = false,columnDefinition = "text")
    private String roomImageUrl;

    public static RoomImage of(RoomImageRequestDto roomImageRequestDto,UUID roomId) {
        return RoomImage.builder()
                .room_id(roomId)
                .build();
    }
}
