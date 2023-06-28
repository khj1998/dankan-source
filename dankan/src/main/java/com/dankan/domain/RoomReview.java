package com.dankan.domain;

import com.dankan.domain.embedded.ResidencePeriod;
import com.dankan.dto.request.review.ReviewRequestDto;
import com.dankan.enum_converter.SatisfyEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
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

    @Column(name = "room_id",nullable = false, columnDefinition = "int")
    private Long roomId;

    @Column(name = "date_id",nullable = false, columnDefinition = "int")
    private Long dateId;

    @Column(name = "total_rate",nullable = false,columnDefinition = "int")
    private Long totalRate;

    @Column(name = "content",nullable = false,columnDefinition = "varchar")
    private String content;

    @UpdateTimestamp
    private LocalDate updatedAt;

    @Column(name = "image_url",columnDefinition = "text")
    private String imageUrl;

    @Embedded
    private ResidencePeriod residencePeriod;

    @Column(name = "address",nullable = false,length = 100,columnDefinition = "varchar")
    private String address;

    @Column(name = "address_detail",nullable = false,length = 50,columnDefinition = "varchar")
    private String addressDetail;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "review_rate",
            joinColumns = {@JoinColumn(name = "review_id", referencedColumnName = "review_id")},
            inverseJoinColumns = {@JoinColumn(name = "code_key", referencedColumnName = "code_key")})
    @ApiModelProperty(example = "사용자 권한 정보들")
    private List<Options> optionsList;

    public static RoomReview of(ReviewRequestDto reviewRequestDto,User user,Long roomId,Long dateId) {
        ResidencePeriod period = ResidencePeriod.builder()
                .startedAt(reviewRequestDto.getStartedAt())
                .endAt(reviewRequestDto.getEndAt())
                .build();
        List<Options> optionsList = getOptionsList(reviewRequestDto);

        return RoomReview.builder()
                .userId(user.getUserId())
                .roomId(roomId)
                .dateId(dateId)
                .totalRate(reviewRequestDto.getTotalRate())
                .content(reviewRequestDto.getContent())
                .residencePeriod(period)
                .address(reviewRequestDto.getAddress())
                .addressDetail(reviewRequestDto.getAddressDetail())
                .optionsList(optionsList)
                .build();
    }

    private static List<Options> getOptionsList(ReviewRequestDto reviewRequestDto) {
        List<Options> optionsList = new ArrayList<>();
        Long cleanRateValue = SatisfyEnum.getSatisfyValue(reviewRequestDto.getCleanRate());
        Long noiseRateValue = SatisfyEnum.getSatisfyValue(reviewRequestDto.getNoiseRate());
        Long accessRateValue = SatisfyEnum.getSatisfyValue(reviewRequestDto.getAccessRate());
        Long hostRateValue = SatisfyEnum.getSatisfyValue(reviewRequestDto.getHostRate());
        Long facilityRateValue = SatisfyEnum.getSatisfyValue(reviewRequestDto.getFacilityRate());

        optionsList.add(Options.builder()
                .codeKey(reviewRequestDto.getCleanRate())
                .value(cleanRateValue)
                .build());

        optionsList.add(Options.builder()
                .codeKey(reviewRequestDto.getNoiseRate())
                .value(noiseRateValue)
                .build());

        optionsList.add(Options.builder()
                .codeKey(reviewRequestDto.getAccessRate())
                .value(accessRateValue)
                .build());

        optionsList.add(Options.builder()
                .codeKey(reviewRequestDto.getHostRate())
                .value(hostRateValue)
                .build());

        optionsList.add(Options.builder()
                .codeKey(reviewRequestDto.getFacilityRate())
                .value(facilityRateValue)
                .build());

        return optionsList;
    }
}
