package com.dankan.enum_converter;

import com.dankan.exception.type.InvalidEtcOptionTypeException;
import com.dankan.exception.type.InvalidManagementTypeException;
import lombok.Getter;

@Getter
public enum ManagementTypeEnum {
    전기세(0L),
    가스비(1L),
    수도세(2L),
    인터넷(3L),
    TV(4L);

    private final Long value;

    ManagementTypeEnum(Long value) {
        this.value = value;
    }

    public static Long getManagementTypeValue(String inputType) {
        for (ManagementTypeEnum type : ManagementTypeEnum.values()) {
            if (type.name().equals(inputType)) {
                return type.getValue();
            }
        }
        throw new InvalidManagementTypeException(inputType);
    }

    public static String getManagementTypeName(Long inputValue) {
        for (ManagementTypeEnum type : ManagementTypeEnum.values()) {
            if (type.getValue().equals(inputValue)) {
                return type.name();
            }
        }
        throw new InvalidManagementTypeException(inputValue.toString());
    }
}
