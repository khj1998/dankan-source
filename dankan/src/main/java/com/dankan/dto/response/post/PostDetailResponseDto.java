package com.dankan.dto.response.post;

import com.dankan.domain.Options;
import com.dankan.domain.Post;
import com.dankan.domain.PostHeart;
import com.dankan.domain.Room;
import com.dankan.enum_converter.*;
import lombok.*;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostDetailResponseDto {
    // post 응답
    private Long postId;
    private LocalDate updatedAt;
    private String title;
    private String content;
    private Boolean isHearted;

    // room 응답
    private Long itemNumber;
    private String address;
    private String addressDetails;
    private String buildingName;
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
    private LocalDate moveInStart; //입주 가능 시작일
    private LocalDate moveInEnd; //입주 가능 마지막 일
    private String imgUrls;

    // room_address 응답
    //private UUID roomId;
    private String doo;
    private String si;
    private String gu;
    private String dong;
    private Double latitude;
    private Double longitude;

    public static PostDetailResponseDto of(Post post, Room room, PostHeart postHeart, String imgUrls, List<Options> optionsList) {
        Boolean isHearted = false;
        String dealType = "";
        String roomType = "";
        String priceType = "";
        String structure = "";
        String managementType = "";
        String options = "";
        String etcOptions = "";

        if (postHeart!=null) {
            isHearted = true;
        }

        for (Options option : optionsList) {
            if (option.getCodeKey().equals("RoomType")) {
                roomType = RoomTypeEnum.getRoomTypeName(option.getValue());
            }

            if (option.getCodeKey().equals("DealType")) {
                dealType = DealTypeEnum.getDealTypeName(option.getValue());
            }

            if (option.getCodeKey().equals("PriceType")) {
                priceType = PriceTypeEnum.getPriceTypeName(option.getValue());
            }

            if (option.getCodeKey().equals("Structure")) {
                structure = StructureTypeEnum.getStructureTypeName(option.getValue());
            }

            if (option.getCodeKey().equals("ManagementType")) {
                for (int i = 0;i<option.getValue().length();i++) {
                    managementType += ManagementTypeEnum.getManagementTypeName(String.valueOf(option.getValue().charAt(i)))+" ";
                }
            }

            if (option.getCodeKey().equals("Option")) {
                for (int i = 0;i<option.getValue().length();i++) {
                    options += OptionTypeEnum.getOptionTypeName(String.valueOf(option.getValue().charAt(i)))+" ";
                }
            }

            if (option.getCodeKey().equals("EtcOption")) {
                for (int i = 0;i<option.getValue().length();i++) {
                    etcOptions += EtcOptionTypeEnum.getEtcOptionTypeName(String.valueOf(option.getValue().charAt(i)))+" ";
                }
            }
        }

        return PostDetailResponseDto.builder()
                .postId(post.getPostId())
                .updatedAt(post.getUpdatedAt())
                .title(post.getTitle())
                .content(post.getContent())
                .isHearted(isHearted)

                .isDiscussion(room.getRoomDiscussion().getIsDiscussion())
                .elevators(room.getElevatorOption())
                .roomType(roomType)
                .dealType(dealType)
                .priceType(priceType)
                .structure(structure)
                .managementType(managementType)
                .options(options)
                .etcOptions(etcOptions)
                .itemNumber(room.getRoomId())
                .address(room.getRoomAddress().getAddress())
                .addressDetails(room.getRoomAddress().getAddressDetail())
                .buildingName(room.getRoomAddress().getBuildingName())
                .deposit(room.getRoomCost().getDeposit())
                .price(room.getRoomCost().getPrice())
                .managementCost(room.getRoomCost().getManagementCost())
                .totalFloor(room.getRoomStructure().getTotalFloor())
                .floor(room.getRoomStructure().getFloor())
                .roomSize(room.getRoomStructure().getRoomSize())
                .realRoomSize(room.getRoomStructure().getRealRoomSize())
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
