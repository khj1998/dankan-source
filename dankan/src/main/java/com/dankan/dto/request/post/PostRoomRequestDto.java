package com.dankan.dto.request.post;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostRoomRequestDto {
    // 게시물 속성
    private Long postId;
    private String title;
    private String content;

    // 매물 속성 
    private String address;
    private String addressDetails;
    private Double latitude;
    private Double longitude;
    private Boolean dealType;
    private String roomType; 
    private Long elevators; // 엘레베이터 유뮤 없으면 0 있으면 1
    private String priceType; // 주 월세 전세 반전세
    private Long deposit; //보증금
    private Long price; // 세
    private Double managementCost; //관리비
    private String managementType; //관리비 종류
    private Long totalFloor;
    private Long floor;
    private String structure;
    private Double roomSize; //실 평수
    private Double realRoomSize; //전용면적
    private String options;
    private String etcOptions;
    private Long isDiscussion; //입주기간 협의 가능여부
    private LocalDate moveInStart; //입주 가능 시작일
    private LocalDate moveInEnd; //입주 가능 마지막 일
}
