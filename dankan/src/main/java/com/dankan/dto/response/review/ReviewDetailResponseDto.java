package com.dankan.dto.response.review;

import com.dankan.domain.Image;
import com.dankan.domain.RoomReview;
import com.dankan.domain.User;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
    private LocalDate startedAt;
    private LocalDate endAt;
    private Double totalRate;
    private String content;
    private LocalDateTime updatedAt;
    private String imgUrl;

    public static ReviewDetailResponseDto of(User user, RoomReview roomReview, List<Image> imageList) {
        String imgUrls = "";

        for (Image img : imageList) {
            imgUrls += img.getImageUrl()+" ";
        }

        return ReviewDetailResponseDto.builder()
                .nickname(user.getNickname())
                .totalRate(roomReview.getTotalRate())
                .startedAt(roomReview.getResidencePeriod().getStartedAt())
                .endAt(roomReview.getResidencePeriod().getEndAt())
                .content(roomReview.getContent())
                .updatedAt(roomReview.getUpdatedAt())
                .imgUrl(imgUrls)
                .build();
    }
}
