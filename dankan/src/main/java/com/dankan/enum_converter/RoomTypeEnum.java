package com.dankan.enum_converter;

import com.dankan.exception.type.InvalidRoomTypeException;
import lombok.Getter;

@Getter
public enum RoomTypeEnum {
    원룸(0L),
    투룸(1L),
    쓰리룸이상(2L);

    private final Long value;

    RoomTypeEnum(Long value) {
        this.value = value;
    }

    public static Long getRoomTypeValue(String inputType) {
        for (RoomTypeEnum type : RoomTypeEnum.values()) {
            if (type.name().equals(inputType)) {
                return type.getValue();
            }
        }
        throw new InvalidRoomTypeException(inputType);
    }

    public static String getRoomTypeName(Long inputValue) {
        for (RoomTypeEnum type : RoomTypeEnum.values()) {
            if (type.getValue().equals(inputValue)) {
                return type.name();
            }
        }
        throw new InvalidRoomTypeException(inputValue.toString());
    }
}
