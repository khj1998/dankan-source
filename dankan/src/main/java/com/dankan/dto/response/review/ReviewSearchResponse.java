package com.dankan.dto.response.review;

import com.dankan.domain.RoomReview;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReviewSearchResponse {
    private String address;
    private Double avgTotalRate;
    private Long detailReviewCount;
    private String imgUrl;

    public static ReviewSearchResponse of(List<RoomReview> roomReviewList,String imgUrl) {
        Double avgTotalRate = 0.0;

        for (RoomReview roomReview : roomReviewList) {
            avgTotalRate += roomReview.getTotalRate();
        }

        if (roomReviewList.size() > 0) {
            avgTotalRate = avgTotalRate / (double) roomReviewList.size();
        }

        return ReviewSearchResponse.builder()
                .address(roomReviewList.get(0).getAddress())
                .avgTotalRate(Math.round(avgTotalRate*10)/10.0)
                .detailReviewCount((long) roomReviewList.size())
                .imgUrl(imgUrl)
                .build();
    }
}
