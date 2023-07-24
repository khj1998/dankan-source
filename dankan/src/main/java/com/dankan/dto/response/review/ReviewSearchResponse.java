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
    private String buildingName;
    private String address;
    private Double avgTotalRate;
    private Long detailReviewCount;
    private String imgUrl;

    public static ReviewSearchResponse of(List<RoomReview> roomReviewList,String imgUrl) {
        Double avgTotalRate = 0.0;
        String buildingName = roomReviewList.get(0).getAddress().split(" ")[4];

        return ReviewSearchResponse.builder()
                .buildingName(buildingName)
                .address(roomReviewList.get(0).getAddress())
                .avgTotalRate(avgTotalRate)
                .detailReviewCount((long) roomReviewList.size())
                .imgUrl(imgUrl)
                .build();
    }
}
