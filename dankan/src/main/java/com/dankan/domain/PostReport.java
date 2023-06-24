package com.dankan.domain;

import io.swagger.annotations.ApiModel;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@ApiModel(value = "게시물 신고 엔티티")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "post_report")
public class PostReport {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "report_id",nullable = false,length = 36,columnDefinition = "varchar")
    @Type(type = "uuid-char")
    private UUID reportId;

    @Column(name="user_id",nullable = false,length = 36,columnDefinition = "varchar")
    @Type(type = "uuid-char")
    private UUID userId;

    @Column(name = "report_type",nullable = false,columnDefinition = "int")
    private String reportType;

    @Column(name = "address",nullable = false,length = 100,columnDefinition = "varchar")
    private String address;

    @Column(name = "address_detail",nullable = false,length = 50,columnDefinition = "varchar")
    private String addressDetail;

    public static PostReport of(Room room,String reportType,UUID userId) {
        return PostReport.builder()
                .userId(userId)
                .reportType(reportType)
                .address(room.getRoomAddress().getAddress())
                .addressDetail(room.getRoomAddress().getAddressDetail())
                .build();
    }
}
