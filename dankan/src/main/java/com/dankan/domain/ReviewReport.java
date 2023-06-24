package com.dankan.domain;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@ApiModel(value = "리뷰 신고 엔티티")
@Getter
@Entity
@Table(name = "review_report")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewReport {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "report_id",nullable = false,length = 36,columnDefinition = "varchar")
    @Type(type = "uuid-char")
    private UUID reportId;

    @Column(name = "user_id",nullable = false,length = 36,columnDefinition = "varchar")
    @Type(type = "uuid-char")
    private UUID userId;

    @Column(name = "report_type",nullable = false,length = 36,columnDefinition = "varchar")
    private String reportType;

    @Column(name = "address",nullable = false,length = 100,columnDefinition = "varchar")
    private String address;

    @Column(name = "address_detail",nullable = false,length = 50,columnDefinition = "varchar")
    private String addressDetail;

    public static ReviewReport of(UUID userId,String reportType,RoomReview roomReview) {
        return ReviewReport.builder()
                .userId(userId)
                .address(roomReview.getAddress())
                .addressDetail(roomReview.getAddressDetail())
                .reportType(reportType)
                .build();
    }
}
