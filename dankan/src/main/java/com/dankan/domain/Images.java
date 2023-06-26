package com.dankan.domain;

import io.swagger.annotations.ApiModel;
import lombok.*;

import javax.persistence.*;

@ApiModel(value = "매매 게시물 엔티티")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "images")
public class Images {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id", columnDefinition = "int")
    private Long imageId;

    @Column(name = "uri", columnDefinition = "text")
    private String uri;
}
