package com.dankan.enum_converter;

import com.dankan.exception.type.InvalidOptionTypeException;
import com.dankan.exception.type.InvalidPriceTypeException;
import lombok.Getter;

@Getter
public enum OptionTypeEnum {
    에어컨("0"),
    냉장고("1"),
    세탁기("2"),
    가스레인지("3"),
    인덕션("4"),
    전자레인지("5"),
    책상("6"),
    책장("7"),
    침대("8"),
    옷장("9"),
    싱크대("-1"),
    신발장("-2");

    private final String value;

    OptionTypeEnum(String value) {
        this.value = value;
    }

    public static String getOptionTypeValue(String inputType) {
        for (OptionTypeEnum type : OptionTypeEnum.values()) {
            if (type.name().equals(inputType)) {
                return type.getValue();
            }
        }
        throw new InvalidOptionTypeException(inputType);
    }

    public static String getOptionTypeName(String inputValue) {
        for (OptionTypeEnum type : OptionTypeEnum.values()) {
            if (type.getValue().equals(inputValue)) {
                return type.name();
            }
        }
        throw new InvalidOptionTypeException(String.valueOf(inputValue));
    }
}
