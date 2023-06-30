package com.dankan.enum_converter;

import com.dankan.exception.type.InvalidPriceTypeException;
import lombok.Getter;

@Getter
public enum PriceTypeEnum {
    주("0"),
    월세("1"),
    전세("2"),
    반전세("3");

    private final String value;

    PriceTypeEnum(String value) {
        this.value = value;
    }

    public static String getPriceTypeValue(String inputType) {
        for (PriceTypeEnum type : PriceTypeEnum.values()) {
            if (type.name().equals(inputType)) {
                return type.getValue();
            }
        }
        throw new InvalidPriceTypeException(inputType);
    }

    public static String getPriceTypeName(String inputValue) {
        for (PriceTypeEnum type : PriceTypeEnum.values()) {
            if (type.getValue().equals(inputValue)) {
                return type.name();
            }
        }
        throw new InvalidPriceTypeException(inputValue);
    }
}
