package com.dankan.enum_converter;

import com.dankan.exception.type.InvalidDealTypeException;
import lombok.Getter;

@Getter
public enum DealTypeEnum {
    단기임대("0"),
    양도("1");

    private final String value;

    DealTypeEnum(String value) {
        this.value = value;
    }

    public static String getDealTypeValue(String inputType) {
        for (DealTypeEnum type : DealTypeEnum.values()) {
            if (type.name().equals(inputType)) {
                return type.getValue();
            }
        }
        throw new InvalidDealTypeException(inputType);
    }

    public static String getDealTypeName(String inputValue) {
        for (DealTypeEnum type : DealTypeEnum.values()) {
            if (type.getValue().equals(inputValue)) {
                return type.name();
            }
        }
        throw new InvalidDealTypeException(inputValue);
    }
}
