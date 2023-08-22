package com.dankan.dto.request.post;

import lombok.Getter;

import java.util.List;

@Getter
public class PostFilterRequestDto {
    private String univ;
    private String address;
    private String dealType;
    private List<String> roomType;
    private String priceType;
    private Long minPayPrice;
    private Long maxPayPrice;
    private Long minDeposit;
    private Long maxDeposit;
    private Long minManagementCost;
    private Long maxManagementCost;
    private List<Long> floorList;
    private List<String> roomStructure;
    private Double minRoomSize;
    private Double maxRoomSize;
    private String canPark;
    private String elevator;
    private String pet;
    private String fullOption;
    private String onlyWomen;
    private String loan;
    private Boolean canDeal;
    private String lowCostOrder;
    private String heartOrder;
}
