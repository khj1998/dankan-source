package com.dankan.domain;

import com.dankan.domain.embedded.RoomAddress;
import com.dankan.domain.embedded.RoomCost;
import com.dankan.domain.embedded.RoomDiscussion;
import com.dankan.domain.embedded.RoomOption;
import com.dankan.domain.embedded.RoomStructure;
import com.dankan.dto.request.post.PostRoomCreateRequestDto;
import com.dankan.enum_converter.DealTypeEnum;
import com.dankan.enum_converter.PriceTypeEnum;
import com.dankan.enum_converter.RoomTypeEnum;
import io.swagger.annotations.ApiModel;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@ApiModel(value = "매물 엔티티")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "room")
public class Room {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "room_id",nullable = false,length = 36,columnDefinition = "varchar")
    @Type(type = "uuid-char")
    private UUID roomId;

    @Column(name = "user_id",nullable = false,length = 36,columnDefinition = "varchar")
    @Type(type = "uuid-char")
    private UUID userId;

    @Embedded
    private RoomCost roomCost;

    @Embedded
    private RoomStructure roomStructure;

    @Embedded
    private RoomOption roomOption;

    @Embedded
    private RoomDiscussion roomDiscussion;

    @Embedded
    private RoomAddress roomAddress;

    public static Room of(PostRoomCreateRequestDto postRoomCreateRequestDto, UUID userId) {
        String[] addressParts = postRoomCreateRequestDto.getAddress().split(" ");

        RoomCost cost = RoomCost.builder()
                .dealType(postRoomCreateRequestDto.getDealType())
                .priceType(PriceTypeEnum.getPriceTypeValue(postRoomCreateRequestDto.getPriceType()))
                .deposit(postRoomCreateRequestDto.getDeposit())
                .price(postRoomCreateRequestDto.getPrice())
                .managementCost(postRoomCreateRequestDto.getManagementCost())
                .managementType(postRoomCreateRequestDto.getManagementType())
                .build();

        RoomStructure structure = RoomStructure.builder()
                .roomType(RoomTypeEnum.getRoomTypeValue(postRoomCreateRequestDto.getRoomType()))
                .totalFloor(postRoomCreateRequestDto.getTotalFloor())
                .floor(postRoomCreateRequestDto.getFloor())
                .structure(postRoomCreateRequestDto.getStructure())
                .roomSize(postRoomCreateRequestDto.getRoomSize())
                .realRoomSize(postRoomCreateRequestDto.getRealRoomSize())
                .build();

        RoomOption option = RoomOption.builder()
                .elevatorOption(postRoomCreateRequestDto.getElevators())
                .roomOptions(postRoomCreateRequestDto.getOptions())
                .roomEtcOptions(postRoomCreateRequestDto.getEtcOptions())
                .build();

        RoomDiscussion discussion = RoomDiscussion.builder()
                .isDiscussion(postRoomCreateRequestDto.getIsDiscussion())
                .moveInStart(postRoomCreateRequestDto.getMoveInStart())
                .moveInEnd(postRoomCreateRequestDto.getMoveInEnd())
                .build();


        RoomAddress address = RoomAddress.builder()
                .doo(addressParts[0])
                .si(addressParts[1])
                .gu(addressParts[2])
                .dong(addressParts[3])
                .latitude(postRoomCreateRequestDto.getLatitude())
                .longitude(postRoomCreateRequestDto.getLongitude())
                .address(postRoomCreateRequestDto.getAddress())
                .addressDetail(postRoomCreateRequestDto.getAddressDetail())
                .build();

        return Room.builder()
                .userId(userId)
                .roomCost(cost)
                .roomStructure(structure)
                .roomOption(option)
                .roomDiscussion(discussion)
                .roomAddress(address)
                .build();
    }
}
