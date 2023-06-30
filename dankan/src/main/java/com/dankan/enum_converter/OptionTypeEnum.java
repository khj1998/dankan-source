package com.dankan.enum_converter;

import com.dankan.exception.type.InvalidOptionTypeException;
import com.dankan.exception.type.InvalidPriceTypeException;
import lombok.Getter;

@Getter
public enum OptionTypeEnum {
    에어컨(0L),
    냉장고(1L),
    세탁기(2L),
    가스레인지(3L),
    인덕션(4L),
    전자레인지(5L),
    책상(6L),
    책장(7L),
    침대(8L),
    옷장(9L),
    싱크대(10L),
    신발장(11L);

    private final Long value;

    OptionTypeEnum(Long value) {
        this.value = value;
    }

    public static Long getOptionTypeValue(String inputType) {
        for (OptionTypeEnum type : OptionTypeEnum.values()) {
            if (type.name().equals(inputType)) {
                return type.getValue();
            }
        }
        throw new InvalidOptionTypeException(inputType);
    }

    public static String getOptionTypeName(Long inputValue) {
        for (OptionTypeEnum type : OptionTypeEnum.values()) {
            if (type.getValue().equals(inputValue)) {
                return type.name();
            }
        }
        throw new InvalidOptionTypeException(inputValue.toString());
    }
}
