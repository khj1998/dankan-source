package com.dankan.enum_converter;

import com.dankan.exception.type.InvalidRoomTypeException;
import lombok.Getter;

@Getter
public enum RoomTypeEnum {
    원룸("0"),
    투룸("1"),
    쓰리룸이상("2");

    private final String value;

    RoomTypeEnum(String value) {
        this.value = value;
    }

    public static String getRoomTypeValue(String inputType) {
        for (RoomTypeEnum type : RoomTypeEnum.values()) {
            if (type.name().equals(inputType)) {
                return type.getValue();
            }
        }
        throw new InvalidRoomTypeException(inputType);
    }

    public static String getRoomTypeName(String inputValue) {
        for (RoomTypeEnum type : RoomTypeEnum.values()) {
            if (type.getValue().equals(inputValue)) {
                return type.name();
            }
        }
        throw new InvalidRoomTypeException(inputValue);
    }
}
