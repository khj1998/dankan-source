package com.dankan.dto.response.post;

import com.dankan.domain.Options;
import com.dankan.domain.Post;
import com.dankan.domain.PostHeart;
import com.dankan.domain.Room;
import com.dankan.enum_converter.PriceTypeEnum;
import com.dankan.enum_converter.StructureTypeEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostResponseDto {
    private Long postId;
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
        String dealType = "";
        String priceType = "";
        String structure = "";

        for (Options options : room.getOptionsList()) {
            if (options.getCodeKey().contains("DealType")) {
                dealType = options.getValue().equals(0L) ? "단기임대" : "양도";
            }

            if (options.getCodeKey().contains("PriceType")) {
                priceType = PriceTypeEnum.getPriceTypeName(options.getValue());
            }

            if (options.getCodeKey().contains("Structure")) {
                structure = StructureTypeEnum.getStructureTypeName(options.getValue());
            }
        }

        return PostResponseDto.builder()
                .postId(post.getPostId())
                .dealType(dealType)
                .priceType(priceType)
                .structure(structure)
                .isHearted(isHearted)
                .price(room.getRoomCost().getPrice())
                .deposit(room.getRoomCost().getDeposit())
                .address(room.getRoomAddress().getAddress())
                .floor(room.getRoomStructure().getFloor())
                .roomSize(room.getRoomStructure().getRoomSize())
                .roomRealSize(room.getRoomStructure().getRealRoomSize())
                .build();
    }

    public static PostResponseDto of(Post post,Room room) {
        String dealType = "";
        String priceType = "";
        String structure = "";

        for (Options options : room.getOptionsList()) {
            if (options.getCodeKey().contains("DealType")) {
                dealType = options.getValue().equals(0L) ? "단기임대" : "양도";
            }

            if (options.getCodeKey().contains("PriceType")) {
                priceType = PriceTypeEnum.getPriceTypeName(options.getValue());
            }

            if (options.getCodeKey().contains("Structure")) {
                structure = StructureTypeEnum.getStructureTypeName(options.getValue());
            }
        }

        return PostResponseDto.builder()
                .postId(post.getPostId())
                .dealType(dealType)
                .priceType(priceType)
                .structure(structure)
                .price(room.getRoomCost().getPrice())
                .deposit(room.getRoomCost().getDeposit())
                .address(room.getRoomAddress().getAddress())
                .floor(room.getRoomStructure().getFloor())
                .roomSize(room.getRoomStructure().getRoomSize())
                .roomRealSize(room.getRoomStructure().getRealRoomSize())
                .build();
    }
}
