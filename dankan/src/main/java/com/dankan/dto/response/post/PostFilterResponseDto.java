package com.dankan.dto.response.post;

import com.dankan.domain.Options;
import com.dankan.domain.Post;
import com.dankan.domain.PostHeart;
import com.dankan.domain.Room;
import com.dankan.enum_converter.DealTypeEnum;
import com.dankan.enum_converter.PriceTypeEnum;
import com.dankan.enum_converter.StructureTypeEnum;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostFilterResponseDto {
    private Long postId;
    private String dealType;
    private Boolean isHearted;
    private String priceType;
    private Long price;
    private Long deposit;
    private String address;
    private String buildingName;
    private String structure;
    private Long floor;
    private Double roomSize;
    private Double roomRealSize;
    private String imgUrl;
    private LocalDateTime createdAt;

    public static PostFilterResponseDto of(Post post, Room room, PostHeart postHeart, String imgUrl, List<Options> optionsList) {
        Boolean isHearted = postHeart!=null;
        String dealType = "";
        String priceType = "";
        String structure = "";

        for (Options options : optionsList) {
            if (options.getCodeKey().contains("DealType")) {
                dealType = DealTypeEnum.getDealTypeName(options.getValue());
            }

            if (options.getCodeKey().contains("PriceType")) {
                priceType = PriceTypeEnum.getPriceTypeName(options.getValue());
            }

            if (options.getCodeKey().contains("Structure")) {
                structure = StructureTypeEnum.getStructureTypeName(options.getValue());
            }
        }

        return PostFilterResponseDto.builder()
                .createdAt(post.getCreatedAt())
                .postId(post.getPostId())
                .dealType(dealType)
                .priceType(priceType)
                .structure(structure)
                .isHearted(isHearted)
                .price(room.getRoomCost().getPrice())
                .deposit(room.getRoomCost().getDeposit())
                .address(room.getRoomAddress().getAddress())
                .buildingName(room.getRoomAddress().getBuildingName())
                .floor(room.getRoomStructure().getFloor())
                .roomSize(room.getRoomStructure().getRoomSize())
                .roomRealSize(room.getRoomStructure().getRealRoomSize())
                .imgUrl(imgUrl)
                .createdAt(post.getCreatedAt())
                .build();
    }
}
