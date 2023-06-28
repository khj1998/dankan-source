package com.dankan.enum_converter;

import com.dankan.exception.type.InvalidSatisfyException;
import lombok.Getter;

@Getter
public enum SatisfyEnum {

    만족(5L),
    보통(3L),
    불만족(1L);

    private final Long value;

    SatisfyEnum(Long value) {
        this.value = value;
    }

    public static Long getSatisfyValue(String inputType) {
        for (SatisfyEnum type : SatisfyEnum.values()) {
            if (type.name().equals(inputType)) {
                return type.getValue();
            }
        }
        throw new InvalidSatisfyException(inputType);
    }
}
