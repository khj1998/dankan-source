package com.dankan.domain;

import com.dankan.domain.embedded.OptionsId;
import com.dankan.dto.request.post.PostRoomRequestDto;
import com.dankan.dto.request.review.ReviewRequestDto;
import com.dankan.enum_converter.*;
import io.swagger.annotations.ApiModel;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@ApiModel(value = "매매 게시물 엔티티")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "options")
@IdClass(OptionsId.class)
public class Options {
    @Id
    @Column(name = "code_key",length = 16,columnDefinition = "varchar")
    private String codeKey;

    @Id
    @Column(name = "room_id",nullable = false,columnDefinition = "int")
    private Long roomId;

    @Column(name = "value",length = 16,nullable = false,columnDefinition = "varchar")
    private String value;

    public static List<Options> of(Long roomId, PostRoomRequestDto postRoomRequestDto) {
        List<Options> optionsList = new ArrayList<>();
        String[] managementTypes = postRoomRequestDto.getManagementType().split(" ");
        String[] options = postRoomRequestDto.getOptions().split(" ");
        String[] etcOptions = postRoomRequestDto.getEtcOptions().split(" ");

        String dealType = postRoomRequestDto.getDealType() ? "0" : "1";
        String roomType = RoomTypeEnum.getRoomTypeValue(postRoomRequestDto.getRoomType());
        String priceType = PriceTypeEnum.getPriceTypeValue(postRoomRequestDto.getPriceType());
        String structureType = StructureTypeEnum.getStructureTypeValue(postRoomRequestDto.getStructure());;

        String managementTypeValue = "";
        String optionsValue = "";
        String etcOptionsValue = "";

        optionsList.add(Options.builder()
                .codeKey("DealType")
                .roomId(roomId)
                .value(dealType)
                .build());

        optionsList.add(Options.builder()
                .codeKey("RoomType")
                .roomId(roomId)
                .value(roomType)
                .build());

        optionsList.add(Options.builder()
                .codeKey("PriceType")
                .roomId(roomId)
                .value(priceType)
                .build());

        optionsList.add(Options.builder()
                .codeKey("StructureType")
                .roomId(roomId)
                .value(structureType)
                .build());

        for (String managementType : managementTypes) {
            managementTypeValue+=ManagementTypeEnum.getManagementTypeValue(managementType);
        }

        optionsList.add(Options.builder()
                .codeKey("ManagementType")
                .roomId(roomId)
                .value(managementTypeValue)
                .build());

        for (String option : options) {
            optionsValue+=OptionTypeEnum.getOptionTypeValue(option);
        }

        optionsList.add(Options.builder()
                .codeKey("Option")
                .roomId(roomId)
                .value(optionsValue)
                .build());

        for (String etcOption : etcOptions) {
            etcOptionsValue+=EtcOptionTypeEnum.getEtcOptionTypeValue(etcOption);
        }

        optionsList.add(Options.builder()
                .codeKey("EtcOption")
                .roomId(roomId)
                .value(etcOptionsValue)
                .build());

        return optionsList;
    }

    public static List<Options> of(Long roomId, ReviewRequestDto reviewRequestDto) {
        List<Options> optionsList = new ArrayList<>();

        String cleanRateValue = SatisfyEnum.getSatisfyValue(reviewRequestDto.getCleanRate());
        String noiseRateValue = SatisfyEnum.getSatisfyValue(reviewRequestDto.getNoiseRate());
        String accessRateValue = SatisfyEnum.getSatisfyValue(reviewRequestDto.getAccessRate());
        String hostRateValue = SatisfyEnum.getSatisfyValue(reviewRequestDto.getHostRate());
        String facilityRateValue = SatisfyEnum.getSatisfyValue(reviewRequestDto.getFacilityRate());

        optionsList.add(Options.builder()
                .codeKey("CleanRate")
                .roomId(roomId)
                .value(cleanRateValue)
                .build());

        optionsList.add(Options.builder()
                .codeKey("NoiseRate")
                .roomId(roomId)
                .value(noiseRateValue)
                .build());

        optionsList.add(Options.builder()
                .codeKey("AccessRate")
                .roomId(roomId)
                .value(accessRateValue)
                .build());

        optionsList.add(Options.builder()
                .codeKey("HostRate")
                .roomId(roomId)
                .value(hostRateValue)
                .build());

        optionsList.add(Options.builder()
                .codeKey("FacilityRate")
                .roomId(roomId)
                .value(facilityRateValue)
                .build());

        return optionsList;
    }
}
