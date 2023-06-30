package com.dankan.enum_converter;

import com.dankan.exception.type.InvalidEtcOptionTypeException;
import com.dankan.exception.type.InvalidManagementTypeException;
import lombok.Getter;

@Getter
public enum ManagementTypeEnum {
    전기세("0"),
    가스비("1"),
    수도세("2"),
    인터넷("3"),
    TV("4");

    private final String value;

    ManagementTypeEnum(String value) {
        this.value = value;
    }

    public static String getManagementTypeValue(String inputType) {
        for (ManagementTypeEnum type : ManagementTypeEnum.values()) {
            if (type.name().equals(inputType)) {
                return type.getValue();
            }
        }
        throw new InvalidManagementTypeException(inputType);
    }

    public static String getManagementTypeName(String inputValue) {
        for (ManagementTypeEnum type : ManagementTypeEnum.values()) {
            if (type.getValue().equals(inputValue)) {
                return type.name();
            }
        }
        throw new InvalidManagementTypeException(inputValue);
    }
}
