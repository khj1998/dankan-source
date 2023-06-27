package com.dankan.enum_converter;

import com.dankan.exception.type.InvalidEtcOptionTypeException;
import com.dankan.exception.type.InvalidOptionTypeException;
import lombok.Getter;

@Getter
public enum EtcOptionTypeEnum {
    주차공간(0L),
    반려동물(1L),
    여성전용(2L),
    전세대출(3L);

    private final Long value;

    EtcOptionTypeEnum(Long value) {
        this.value = value;
    }

    public static Long getEtcOptionTypeValue(String inputType) {
        for (EtcOptionTypeEnum type : EtcOptionTypeEnum.values()) {
            if (type.name().equals(inputType)) {
                return type.getValue();
            }
        }
        throw new InvalidEtcOptionTypeException(inputType);
    }

    public static String getEtcOptionTypeName(Long inputValue) {
        for (EtcOptionTypeEnum type : EtcOptionTypeEnum.values()) {
            if (type.getValue().equals(inputValue)) {
                return type.name();
            }
        }
        throw new InvalidEtcOptionTypeException(inputValue.toString());
    }
}
