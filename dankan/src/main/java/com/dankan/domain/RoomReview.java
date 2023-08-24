package com.dankan.domain;

import com.dankan.domain.embedded.ResidencePeriod;
import com.dankan.dto.request.review.ReviewRequestDto;
import com.dankan.enum_converter.SatisfyEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id", columnDefinition = "int")
    private Long reviewId;

    @Column(name = "user_id",nullable = false, columnDefinition = "bigint")
    private Long userId;

    @Column(name = "image_id",columnDefinition = "int")
    private Long imageId;

    @Column(name = "date_id",nullable = false, columnDefinition = "int")
    private Long dateId;

    @Column(name = "content",nullable = false,columnDefinition = "varchar")
    private String content;

    @CreationTimestamp
    private LocalDate createdAt;

    @UpdateTimestamp
    private LocalDate updatedAt;

    @Embedded
    private ResidencePeriod residencePeriod;

    @Column(name = "address",nullable = false,length = 100,columnDefinition = "varchar")
    private String address;

    @Column(name = "address_detail",length = 50,columnDefinition = "varchar")
    private String addressDetail;

    @Column(name = "total_rate",nullable = false,columnDefinition = "double")
    private Double totalRate;

    @Column(name = "clean_rate",nullable = false,columnDefinition = "int")
    private Long cleanRate;

    @Column(name = "noise_rate",nullable = false,columnDefinition = "int")
    private Long noiseRate;

    @Column(name = "access_rate",nullable = false,columnDefinition = "int")
    private Long accessRate;

    @Column(name = "host_rate",nullable = false,columnDefinition = "int")
    private Long hostRate;

    @Column(name = "facility_rate",nullable = false,columnDefinition = "int")
    private Long facilityRate;

    public static RoomReview of(ReviewRequestDto reviewRequestDto,User user,Long dateId) {
        ResidencePeriod period = ResidencePeriod.builder()
                .startedAt(reviewRequestDto.getStartedAt())
                .endAt(reviewRequestDto.getEndAt())
                .build();

        String cleanRate = SatisfyEnum.getSatisfyValue(reviewRequestDto.getCleanRate());
        String noiseRate = SatisfyEnum.getSatisfyValue(reviewRequestDto.getNoiseRate());
        String accessRate = SatisfyEnum.getSatisfyValue(reviewRequestDto.getAccessRate());
        String hostRate = SatisfyEnum.getSatisfyValue(reviewRequestDto.getHostRate());
        String facilityRate = SatisfyEnum.getSatisfyValue(reviewRequestDto.getFacilityRate());

        return RoomReview.builder()
                .userId(user.getUserId())
                .dateId(dateId)
                .totalRate(reviewRequestDto.getTotalRate())
                .cleanRate(Long.parseLong(cleanRate))
                .noiseRate(Long.parseLong(noiseRate))
                .accessRate(Long.parseLong(accessRate))
                .hostRate(Long.parseLong(hostRate))
                .facilityRate(Long.parseLong(facilityRate))
                .content(reviewRequestDto.getContent())
                .residencePeriod(period)
                .address(reviewRequestDto.getAddress())
                .addressDetail(reviewRequestDto.getAddressDetail())
                .build();
    }
}
