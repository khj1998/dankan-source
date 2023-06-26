package com.dankan.domain;

import com.dankan.domain.embedded.ResidencePeriod;
import com.dankan.domain.embedded.RoomReviewRate;
import com.dankan.dto.request.review.ReviewRequestDto;
import com.dankan.enum_converter.SatisfyEnum;
import io.swagger.annotations.ApiModel;
import lombok.*;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id", columnDefinition = "int")
    private Long reviewId;

    @Column(name = "user_id", columnDefinition = "bigint")
    private Long userId;

    @Column(name = "room_id", columnDefinition = "int")
    private Long roomId;

    @Column(name = "content",nullable = false,columnDefinition = "varchar")
    private String content;

    @UpdateTimestamp
    private Date updatedAt;

    @Embedded
    private ResidencePeriod residencePeriod;

    @Column(name = "address",nullable = false,length = 100,columnDefinition = "varchar")
    private String address;

    @Column(name = "address_detail",nullable = false,length = 50,columnDefinition = "varchar")
    private String addressDetail;

    public static RoomReview of(ReviewRequestDto reviewRequestDto,User user,Long roomId) {
        ResidencePeriod period = ResidencePeriod.builder()
                .startedAt(reviewRequestDto.getStartedAt())
                .endAt(reviewRequestDto.getEndAt())
                .build();

        return RoomReview.builder()
                .userId(user.getUserId())
                .roomId(roomId)
                .content(reviewRequestDto.getContent())
                .residencePeriod(period)
                .address(reviewRequestDto.getAddress())
                .addressDetail(reviewRequestDto.getAddressDetail())
                .build();
    }
}
