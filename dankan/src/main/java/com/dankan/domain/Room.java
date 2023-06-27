package com.dankan.domain;

import com.dankan.domain.embedded.RoomAddress;
import com.dankan.domain.embedded.RoomCost;
import com.dankan.domain.embedded.RoomDiscussion;
import com.dankan.domain.embedded.RoomStructure;
import com.dankan.dto.request.post.PostRoomRequestDto;
import com.dankan.enum_converter.PriceTypeEnum;
import com.dankan.enum_converter.RoomTypeEnum;
import io.swagger.annotations.ApiModel;
import lombok.*;

import javax.persistence.*;

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

    @Column(name = "date_id", columnDefinition = "int")
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

    public static Room of(PostRoomRequestDto postRoomRequestDto, Long userId,Long dateId) {
        String[] addressParts = postRoomRequestDto.getAddress().split(" ");

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
                .roomCost(cost)
                .roomStructure(structure)
                .elevatorOption(postRoomRequestDto.getElevators())
                .roomDiscussion(discussion)
                .roomAddress(address)
                .build();
    }
}
