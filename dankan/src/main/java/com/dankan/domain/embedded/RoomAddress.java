package com.dankan.domain.embedded;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class RoomAddress {
    @Column(name = "dong",nullable = false,length = 16,columnDefinition = "varchar")
    private String dong;

    @Column(name = "si",nullable = false,length = 16,columnDefinition = "varchar")
    private String si;

    @Column(name = "doo",nullable = false,length = 16,columnDefinition = "varchar")
    private String doo;

    @Column(name = "gu",nullable = false,length = 16,columnDefinition = "varchar")
    private String gu;

    @Column(name = "latitude",nullable = false,columnDefinition = "double")
    private Double latitude;

    @Column(name = "longitude",nullable = false,columnDefinition = "double")
    private Double longitude;

    @Column(name = "address",nullable = false,length = 100,columnDefinition = "varchar")
    private String address;

    @Column(name = "address_detail",nullable = false,length = 50,columnDefinition = "varchar")
    private String addressDetail;

    @Column(name = "building_name",nullable = false, length = 128, columnDefinition = "varchar")
    private String buildingName;
}
