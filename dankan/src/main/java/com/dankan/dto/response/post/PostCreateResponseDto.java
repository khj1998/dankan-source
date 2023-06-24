package com.dankan.dto.response.post;

import com.dankan.domain.Post;
import com.dankan.domain.Room;
import com.dankan.enum_converter.PriceTypeEnum;
import com.dankan.enum_converter.RoomTypeEnum;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostCreateResponseDto {
    // post 응답
    private UUID postId;
    private LocalDate updatedAt;
    private String title;
    private String content;
    private Boolean isHearted;

    // room 응답
    private UUID roomId;
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

    // room_address 응답
    //private UUID roomId;
    private String doo;
    private String si;
    private String gu;
    private String dong;
    private Double latitude;
    private Double longitude;

    public static PostCreateResponseDto of(Post post, Room room) {
        LocalDate currentDate = LocalDate.now();

        String dealType;

        if (room.getRoomCost().getDealType()) {
            dealType = "양도";
        } else {
            dealType = "단기임대";
        }

        return PostCreateResponseDto.builder()
                .postId(post.getPostId())
                .updatedAt(currentDate)
                .title(post.getTitle())
                .content(post.getContent())
                .isHearted(false)

                .roomId(room.getRoomId()) // 매물 번호
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
                .doo(room.getRoomAddress().getDoo())
                .si(room.getRoomAddress().getSi())
                .gu(room.getRoomAddress().getGu())
                .dong(room.getRoomAddress().getDong())
                .latitude(room.getRoomAddress().getLatitude())
                .longitude(room.getRoomAddress().getLongitude())
                .address(room.getRoomAddress().getAddress())
                .addressDetails(room.getRoomAddress().getAddressDetail())
                .build();
    }
}
