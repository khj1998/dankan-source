package com.dankan.enum_converter;

import com.dankan.exception.type.InvalidSatisfyException;
import lombok.Getter;

@Getter
public enum SatisfyEnum {

    만족("5"),
    보통("3"),
    불만족("1");

    private final String value;

    SatisfyEnum(String value) {
        this.value = value;
    }

    public static String getSatisfyValue(String inputType) {
        for (SatisfyEnum type : SatisfyEnum.values()) {
            if (type.name().equals(inputType)) {
                return type.getValue();
            }
        }
        throw new InvalidSatisfyException(inputType);
    }
}
