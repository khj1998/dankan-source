package com.dankan.domain;

import com.dankan.enum_converter.ImageTypeEnum;
import io.swagger.annotations.ApiModel;
import lombok.*;

import javax.persistence.*;

@ApiModel(value = "매물 이미지")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "images")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id",columnDefinition = "int")
    private Long imageId;

    @Column(name = "id",nullable = false,columnDefinition = "int")
    private Long id;

    @Column(name = "image_type",columnDefinition = "int")
    private Long imageType;

    @Column(name = "image_url",nullable = false,columnDefinition = "text")
    private String imageUrl;

    public static Image of(String imageType, String imageUrl, Long id) {
        Long imageTypeValue = ImageTypeEnum.getRoomImageTypeValue(imageType);

        return Image.builder()
                .id(id)
                .imageType(imageTypeValue)
                .imageUrl(imageUrl)
                .build();
    }
}
