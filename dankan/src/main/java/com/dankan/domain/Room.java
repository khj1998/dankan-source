package com.dankan.domain;

import com.dankan.domain.embedded.RoomAddress;
import com.dankan.domain.embedded.RoomCost;
import com.dankan.domain.embedded.RoomDiscussion;
import com.dankan.domain.embedded.RoomStructure;
import com.dankan.dto.request.post.PostRoomRequestDto;
import com.dankan.enum_converter.*;
import com.dankan.enum_converter.PriceTypeEnum;
import com.dankan.enum_converter.RoomTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@ApiModel(value = "매물 엔티티")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "room")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id", columnDefinition = "int")
    private Long roomId;

    @Column(name = "user_id",nullable = false, columnDefinition = "bigint")
    private Long userId;

    @Column(name = "date_id",nullable = false, columnDefinition = "int")
    private Long dateId;

    @Column(name = "elevator_option",nullable = false,columnDefinition = "tinyint")
    private Long elevatorOption;

    @Embedded
    private RoomCost roomCost;

    @Embedded
    private RoomStructure roomStructure;

    @Embedded
    private RoomDiscussion roomDiscussion;

    @Embedded
    private RoomAddress roomAddress;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "room_options",
            joinColumns = {@JoinColumn(name = "room_id", referencedColumnName = "room_id")},
            inverseJoinColumns = {@JoinColumn(name = "code_key", referencedColumnName = "code_key")})
    @ApiModelProperty(example = "사용자 권한 정보들")
    private List<Options> optionsList;

    public static Room of(PostRoomRequestDto postRoomRequestDto, Long userId,Long dateId) {
        String[] addressParts = postRoomRequestDto.getAddress().split(" ");
        List<Options> optionsList = getOptionsList(postRoomRequestDto);

        RoomCost cost = RoomCost.builder()
                .deposit(postRoomRequestDto.getDeposit())
                .price(postRoomRequestDto.getPrice())
                .managementCost(postRoomRequestDto.getManagementCost())
                .build();

        RoomStructure structure = RoomStructure.builder()
                .totalFloor(postRoomRequestDto.getTotalFloor())
                .floor(postRoomRequestDto.getFloor())
                .roomSize(postRoomRequestDto.getRoomSize())
                .realRoomSize(postRoomRequestDto.getRealRoomSize())
                .build();

        RoomDiscussion discussion = RoomDiscussion.builder()
                .isDiscussion(postRoomRequestDto.getIsDiscussion())
                .moveInStart(postRoomRequestDto.getMoveInStart())
                .moveInEnd(postRoomRequestDto.getMoveInEnd())
                .build();


        RoomAddress address = RoomAddress.builder()
                .doo(addressParts[0])
                .si(addressParts[1])
                .gu(addressParts[2])
                .dong(addressParts[3])
                .latitude(postRoomRequestDto.getLatitude())
                .longitude(postRoomRequestDto.getLongitude())
                .address(postRoomRequestDto.getAddress())
                .addressDetail(postRoomRequestDto.getAddressDetails())
                .build();

        return Room.builder()
                .userId(userId)
                .dateId(dateId)
                .optionsList(optionsList)
                .roomCost(cost)
                .roomStructure(structure)
                .elevatorOption(postRoomRequestDto.getElevators())
                .roomDiscussion(discussion)
                .roomAddress(address)
                .build();
    }

    private static List<Options> getOptionsList(PostRoomRequestDto postRoomRequestDto) {
        List<Options> optionsList = new ArrayList<>();
        String[] managementTypes = postRoomRequestDto.getManagementType().split(" ");
        String[] options = postRoomRequestDto.getOptions().split(" ");
        String[] etcOptions = postRoomRequestDto.getEtcOptions().split(" ");

        Long dealType = postRoomRequestDto.getDealType() ? 0L : 1L;
        Long roomType = RoomTypeEnum.getRoomTypeValue(postRoomRequestDto.getRoomType());
        Long priceType = PriceTypeEnum.getPriceTypeValue(postRoomRequestDto.getPriceType());
        Long structureType = StructureTypeEnum.getStructureTypeValue(postRoomRequestDto.getStructure());

        optionsList.add(Options.builder()
                .codeKey("DealType"+dealType)
                .value(dealType)
                .build());

        optionsList.add(Options.builder()
                .codeKey("RoomType"+roomType)
                .value(roomType)
                .build());

        optionsList.add(Options.builder()
                .codeKey("PriceType"+priceType)
                .value(priceType)
                .build());

        optionsList.add(Options.builder()
                .codeKey("StructureType"+structureType)
                .value(structureType)
                .build());

        for (String managementType : managementTypes) {
            optionsList.add(Options.builder()
                   .codeKey("ManagementType"+ManagementTypeEnum.getManagementTypeValue(managementType))
                   .value(ManagementTypeEnum.getManagementTypeValue(managementType))
                   .build());
        }

        for (String option : options) {
            optionsList.add(Options.builder()
                    .codeKey("Option"+OptionTypeEnum.getOptionTypeValue(option))
                    .value(OptionTypeEnum.getOptionTypeValue(option))
                    .build());
        }

        for (String etcOption : etcOptions) {
            optionsList.add(Options.builder()
                    .codeKey("EtcOption"+EtcOptionTypeEnum.getEtcOptionTypeValue(etcOption))
                    .value(EtcOptionTypeEnum.getEtcOptionTypeValue(etcOption))
                    .build());
        }

        return optionsList;
    }
}
