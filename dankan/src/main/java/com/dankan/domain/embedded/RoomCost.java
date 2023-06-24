package com.dankan.domain.embedded;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Getter
@Setter
@Embeddable
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomCost {

    @Column(name = "price_type",nullable = false,columnDefinition = "int")
    private Long priceType;

    @Column(name ="deal_type",nullable = false,columnDefinition = "tinyint")
    private Boolean dealType;

    @Column(name = "deposit",nullable = false,columnDefinition = "int")
    private Long deposit;

    @Column(name = "price",nullable = false,columnDefinition = "int")
    private Long price;

    @Column(name = "management_cost",nullable = false,columnDefinition = "double")
    private Double managementCost;

    @Column(name = "managementType",nullable = false,length = 64,columnDefinition = "varchar")
    private String managementType;
}
