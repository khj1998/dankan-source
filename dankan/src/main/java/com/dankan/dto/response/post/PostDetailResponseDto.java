package com.dankan.dto.response.post;

import com.dankan.domain.Post;
import com.dankan.domain.PostHeart;
import com.dankan.domain.Room;
import com.dankan.enum_converter.PriceTypeEnum;
import com.dankan.enum_converter.RoomTypeEnum;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostDetailResponseDto {
    // post 응답
    private UUID postId;
    private Date updatedAt;
    private String title;
    private String content;
    private Boolean isHearted;
    private Integer heartCount;

    // room 응답
    private UUID itemNumber;
    private String address;
    private String addressDetails;
    private String dealType;
    private String roomType;
    private Long elevators;
    private String priceType;
    private Long deposit;
    private Long price;
    private Double managementCost;
    private String managementType;
    private Long totalFloor;
    private Long floor;
    private String structure;
    private Double roomSize;
    private Double realRoomSize;
    private String options;
    private String etcOptions;
    private Long isDiscussion; //입주기간 협의 가능여부
    private Date moveInStart; //입주 가능 시작일
    private Date moveInEnd; //입주 가능 마지막 일
    private String imgUrls;

    // room_address 응답
    //private UUID roomId;
    private String doo;
    private String si;
    private String gu;
    private String dong;
    private Double latitude;
    private Double longitude;

    public static PostDetailResponseDto of(Post post, Room room,PostHeart postHeart,Integer heartCount,String imgUrls) {
        Boolean isHearted = false;
        String dealType;

        if (postHeart!=null) {
            isHearted = true;
        }

        if (room.getRoomCost().getDealType()) {
            dealType = "양도";
        } else {
            dealType = "단기임대";
        }

        return PostDetailResponseDto.builder()
                .postId(post.getPostId())
                .updatedAt(post.getUpdatedAt())
                .title(post.getTitle())
                .content(post.getContent())
                .isHearted(isHearted)
                .heartCount(heartCount)

                .itemNumber(room.getRoomId())
                .address(room.getRoomAddress().getAddress())
                .addressDetails(room.getRoomAddress().getAddressDetail())
                .dealType(dealType)
                .roomType(RoomTypeEnum.getRoomTypeName(room.getRoomStructure().getRoomType()))
                .elevators(room.getRoomOption().getElevatorOption())
                .priceType(PriceTypeEnum.getPriceTypeName(room.getRoomCost().getPriceType()))
                .deposit(room.getRoomCost().getDeposit())
                .price(room.getRoomCost().getPrice())
                .managementCost(room.getRoomCost().getManagementCost())
                .managementType(room.getRoomCost().getManagementType())
                .totalFloor(room.getRoomStructure().getTotalFloor())
                .floor(room.getRoomStructure().getFloor())
                .structure(room.getRoomStructure().getStructure())
                .roomSize(room.getRoomStructure().getRoomSize())
                .realRoomSize(room.getRoomStructure().getRealRoomSize())
                .options(room.getRoomOption().getRoomOptions())
                .etcOptions(room.getRoomOption().getRoomEtcOptions())
                .isDiscussion(room.getRoomDiscussion().getIsDiscussion())
                .moveInStart(room.getRoomDiscussion().getMoveInStart())
                .moveInEnd(room.getRoomDiscussion().getMoveInEnd())
                .imgUrls(imgUrls)

                .doo(room.getRoomAddress().getDoo())
                .si(room.getRoomAddress().getSi())
                .gu(room.getRoomAddress().getGu())
                .dong(room.getRoomAddress().getDong())
                .latitude(room.getRoomAddress().getLatitude())
                .longitude(room.getRoomAddress().getLongitude())
                .build();
    }
}
