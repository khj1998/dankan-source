package com.dankan.enum_converter;

import com.dankan.exception.type.InvalidRoomImageTypeException;
import lombok.Getter;

@Getter
public enum ImageTypeEnum {
    대표사진(0L),
    거실방사진(1L),
    주방화장실사진(2L),
    리뷰사진(3L);

    private final Long value;

    ImageTypeEnum(Long value) {
        this.value = value;
    }

    public static Long getRoomImageTypeValue(String inputType) {
        for (ImageTypeEnum type : ImageTypeEnum.values()) {
            if (type.name().equals(inputType)) {
                return type.getValue();
            }
        }
        throw new InvalidRoomImageTypeException(inputType);
    }

    public static String getRoomImageTypeName(String inputValue) {
        for (ImageTypeEnum type : ImageTypeEnum.values()) {
            if (type.getValue().equals(inputValue)) {
                return type.name();
            }
        }
        throw new InvalidRoomImageTypeException(inputValue);
    }
}
