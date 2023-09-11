package com.dankan.enum_converter;

import com.dankan.exception.type.InvalidEtcOptionTypeException;
import com.dankan.exception.type.InvalidOptionTypeException;
import lombok.Getter;

@Getter
public enum EtcOptionTypeEnum {
    주차가능("0"),
    반려동물("1"),
    여성전용("2"),
    전세대출("3");

    private final String value;

    EtcOptionTypeEnum(String value) {
        this.value = value;
    }

    public static String getEtcOptionTypeValue(String inputType) {
        for (EtcOptionTypeEnum type : EtcOptionTypeEnum.values()) {
            if (type.name().equals(inputType)) {
                return type.getValue();
            }
        }
        throw new InvalidEtcOptionTypeException(inputType);
    }

    public static String getEtcOptionTypeName(String inputValue) {
        for (EtcOptionTypeEnum type : EtcOptionTypeEnum.values()) {
            if (type.getValue().equals(inputValue)) {
                return type.name();
            }
        }
        throw new InvalidEtcOptionTypeException(inputValue);
    }
}
