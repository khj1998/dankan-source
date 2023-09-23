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
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ApiModel(value = "매물 엔티티")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "room")
public class Room {

    @Id
    @Column(name = "room_id", columnDefinition = "bigint")
    private Long roomId;

    @Column(name = "user_id",nullable = false, columnDefinition = "bigint")
    private Long userId;

    @Column(name = "date_id",nullable = false, columnDefinition = "bigint")
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

    @Column(name = "is_tradeable",nullable = false,columnDefinition = "bit")
    private Boolean isTradeable;

    @Column(name = "univ",nullable = false,length = 16,columnDefinition = "varchar")
    private String univ;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public static Room of(PostRoomRequestDto postRoomRequestDto,User user) {
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
                .buildingName(addressParts[4])
                .latitude(postRoomRequestDto.getLatitude())
                .longitude(postRoomRequestDto.getLongitude())
                .address(postRoomRequestDto.getAddress())
                .addressDetail(postRoomRequestDto.getAddressDetails())
                .build();

        return Room.builder()
                .userId(user.getUserId())
                .roomCost(cost)
                .roomStructure(structure)
                .elevatorOption(postRoomRequestDto.getElevators())
                .roomDiscussion(discussion)
                .roomAddress(address)
                .isTradeable(true)
                .univ(user.getUnivEmail())
                .build();
    }
}
