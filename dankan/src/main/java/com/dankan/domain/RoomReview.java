package com.dankan.domain;

import com.dankan.domain.embedded.ResidencePeriod;
import com.dankan.domain.embedded.RoomReviewRate;
import com.dankan.dto.request.review.ReviewRequestDto;
import com.dankan.enum_converter.SatisfyEnum;
import io.swagger.annotations.ApiModel;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@ApiModel(value = "매물 후기 엔티티")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "review")
public class RoomReview {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "review_id",nullable = false,length = 36,columnDefinition = "varchar")
    @Type(type = "uuid-char")
    private UUID reviewId;

    @Column(name = "user_id",nullable = false,length = 36,columnDefinition = "varchar")
    @Type(type = "uuid-char")
    private UUID userId;

    @Column(name = "room_id",nullable = false,length = 36,columnDefinition = "varchar")
    @Type(type = "uuid-char")
    private UUID roomId;

    @Column(name = "content",nullable = false,columnDefinition = "varchar")
    private String content;

    @UpdateTimestamp
    private Date updatedAt;

    @Column(name = "image_url",columnDefinition = "text")
    private String imageUrl;

    @Embedded
    private ResidencePeriod residencePeriod;

    @Embedded
    private RoomReviewRate roomReviewRate;

    @Column(name = "address",nullable = false,length = 100,columnDefinition = "varchar")
    private String address;

    @Column(name = "address_detail",nullable = false,length = 50,columnDefinition = "varchar")
    private String addressDetail;

    public static RoomReview of(ReviewRequestDto reviewRequestDto,User user,UUID roomId) {
        ResidencePeriod period = ResidencePeriod.builder()
                .startedAt(reviewRequestDto.getStartedAt())
                .endAt(reviewRequestDto.getEndAt())
                .build();

        RoomReviewRate reviewRate = RoomReviewRate.builder()
                .totalRate(reviewRequestDto.getTotalRate())
                .accessRate(SatisfyEnum.getSatisfyValue(reviewRequestDto.getAccessRate()))
                .cleanRate(SatisfyEnum.getSatisfyValue(reviewRequestDto.getCleanRate()))
                .hostRate(SatisfyEnum.getSatisfyValue(reviewRequestDto.getHostRate()))
                .facilityRate(SatisfyEnum.getSatisfyValue(reviewRequestDto.getFacilityRate()))
                .noiseRate(SatisfyEnum.getSatisfyValue(reviewRequestDto.getNoiseRate()))
                .build();

        return RoomReview.builder()
                .userId(user.getUserId())
                .roomId(roomId)
                .content(reviewRequestDto.getContent())
                .residencePeriod(period)
                .roomReviewRate(reviewRate)
                .address(reviewRequestDto.getAddress())
                .addressDetail(reviewRequestDto.getAddressDetail())
                .build();
    }
}
