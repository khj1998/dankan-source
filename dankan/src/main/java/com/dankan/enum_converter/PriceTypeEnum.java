package com.dankan.enum_converter;

import com.dankan.exception.type.InvalidPriceTypeException;
import lombok.Getter;

@Getter
public enum PriceTypeEnum {
    주(0L),
    월세(1L),
    전세(2L),
    반전세(3L);

    private final Long value;

    PriceTypeEnum(Long value) {
        this.value = value;
    }

    public static Long getPriceTypeValue(String inputType) {
        for (PriceTypeEnum type : PriceTypeEnum.values()) {
            if (type.name().equals(inputType)) {
                return type.getValue();
            }
        }
        throw new InvalidPriceTypeException(inputType);
    }

    public static String getPriceTypeName(Long inputValue) {
        for (PriceTypeEnum type : PriceTypeEnum.values()) {
            if (type.getValue().equals(inputValue)) {
                return type.name();
            }
        }
        throw new InvalidPriceTypeException(inputValue.toString());
    }
}
