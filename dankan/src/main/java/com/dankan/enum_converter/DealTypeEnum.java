package com.dankan.enum_converter;

import lombok.Getter;

@Getter
public enum DealTypeEnum {
    단기임대(0L),
    양도(1L);

    private final Long value;

    DealTypeEnum(Long value) {
        this.value = value;
    }

    public static Long getDealTypeValue(String inputType) {
        for (DealTypeEnum type : DealTypeEnum.values()) {
            if (type.name().equals(inputType)) {
                return type.getValue();
            }
        }
        return null;
    }

    public static String getDealTypeName(Long inputValue) {
        for (DealTypeEnum type : DealTypeEnum.values()) {
            if (type.getValue().equals(inputValue)) {
                return type.name();
            }
        }
        return null;
    }
}
