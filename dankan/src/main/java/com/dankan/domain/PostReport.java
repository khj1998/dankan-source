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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id", columnDefinition = "int")
    private Long reportId;

    @Column(name="user_id",nullable = false, columnDefinition = "bigint")
    private Long userId;

    @Column(name = "address",nullable = false,length = 100,columnDefinition = "varchar")
    private String address;

    @Column(name = "address_detail",nullable = false,length = 50,columnDefinition = "varchar")
    private String addressDetail;

    @Column(name = "date_id",nullable = false, columnDefinition = "int")
    private Long dateId;

    public static PostReport of(Room room,Long userId,Long dateId) {
        return PostReport.builder()
                .userId(userId)
                .dateId(dateId)
                .address(room.getRoomAddress().getAddress())
                .addressDetail(room.getRoomAddress().getAddressDetail())
                .build();
    }
}
