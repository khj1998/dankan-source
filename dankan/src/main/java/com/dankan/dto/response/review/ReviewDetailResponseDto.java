package com.dankan.dto.response.review;

import com.dankan.domain.RoomReview;
import com.dankan.domain.User;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewDetailResponseDto {
    private String nickname;
    private String univ;
    private Date startedAt;
    private Date endAt;
    private Long totalRate;
    private String content;
    private Date updatedAt;

    public static ReviewDetailResponseDto of(User user, RoomReview roomReview) {
        return ReviewDetailResponseDto.builder()
                .nickname(user.getNickname())
                .startedAt(roomReview.getResidencePeriod().getStartedAt())
                .endAt(roomReview.getResidencePeriod().getEndAt())
                .totalRate(roomReview.getRoomReviewRate().getTotalRate())
                .content(roomReview.getContent())
                .updatedAt(roomReview.getUpdatedAt())
                .build();
    }
}
