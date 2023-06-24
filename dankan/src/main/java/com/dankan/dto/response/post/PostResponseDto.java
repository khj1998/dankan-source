package com.dankan.dto.response.post;

import com.dankan.domain.Post;
import com.dankan.domain.PostHeart;
import com.dankan.domain.Room;
import com.dankan.enum_converter.PriceTypeEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostResponseDto {
    private UUID postId;
    private String dealType;
    private Boolean isHearted;
    private String priceType;
    private Long price;
    private Long deposit;
    private String address;
    private String structure;
    private Long floor;
    private Double roomSize;
    private Double roomRealSize;

    public static PostResponseDto of(Post post, Room room, PostHeart postHeart) {
        Boolean isHearted = postHeart!=null;
        String dealType;

        if (room.getRoomCost().getDealType()) {
            dealType = "양도";
        } else {
            dealType = "단기임대";
        }

        return PostResponseDto.builder()
                .postId(post.getPostId())
                .dealType(dealType)
                .isHearted(isHearted)
                .priceType(PriceTypeEnum.getPriceTypeName(room.getRoomCost().getPriceType()))
                .price(room.getRoomCost().getPrice())
                .deposit(room.getRoomCost().getDeposit())
                .address(room.getRoomAddress().getAddress())
                .structure(room.getRoomStructure().getStructure())
                .floor(room.getRoomStructure().getFloor())
                .roomSize(room.getRoomStructure().getRoomSize())
                .roomRealSize(room.getRoomStructure().getRealRoomSize())
                .build();
    }

    public static PostResponseDto of(Post post,Room room) {
        String dealType;

        if (room.getRoomCost().getDealType()) {
            dealType = "양도";
        } else {
            dealType = "단기임대";
        }

        return PostResponseDto.builder()
                .postId(post.getPostId())
                .dealType(dealType)
                .priceType(PriceTypeEnum.getPriceTypeName(room.getRoomCost().getPriceType()))
                .price(room.getRoomCost().getPrice())
                .deposit(room.getRoomCost().getDeposit())
                .address(room.getRoomAddress().getAddress())
                .structure(room.getRoomStructure().getStructure())
                .floor(room.getRoomStructure().getFloor())
                .roomSize(room.getRoomStructure().getRoomSize())
                .roomRealSize(room.getRoomStructure().getRealRoomSize())
                .build();
    }
}
