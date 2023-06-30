package com.dankan.domain;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@ApiModel(value = "리뷰 신고 엔티티")
@Getter
@Entity
@Table(name = "review_report")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id",nullable = false, columnDefinition = "int")
    private Long reportId;

    @Column(name = "user_id",nullable = false, columnDefinition = "bigint")
    private Long userId;

    @Column(name = "address",nullable = false,length = 100,columnDefinition = "varchar")
    private String address;

    @Column(name = "address_detail",nullable = false,length = 50,columnDefinition = "varchar")
    private String addressDetail;

    @Column(name = "date_id",nullable = false, columnDefinition = "int")
    private Long dateId;

    public static ReviewReport of(Long userId,Long dateId,RoomReview roomReview) {
        return ReviewReport.builder()
                .userId(userId)
                .dateId(dateId)
                .address(roomReview.getAddress())
                .addressDetail(roomReview.getAddressDetail())
                .build();
    }
}
