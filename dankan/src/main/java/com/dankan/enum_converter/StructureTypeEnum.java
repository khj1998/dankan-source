package com.dankan.enum_converter;

import com.dankan.exception.type.InvalidOptionTypeException;
import com.dankan.exception.type.InvalidStructureTypeException;
import lombok.Getter;

@Getter
public enum StructureTypeEnum {
    분리형("0"),
    오픈형("1"),
    복층형("2"),
    직접입력("3"),
    해당없음("4");

    private String value;

    StructureTypeEnum(String value) {
        this.value = value;
    }

    public static String getStructureTypeValue(String inputType) {
        for (StructureTypeEnum type : StructureTypeEnum.values()) {
            if (type.name().equals(inputType)) {
                return type.getValue();
            }
        }
        throw new InvalidStructureTypeException(inputType);
    }

    public static String getStructureTypeName(String inputValue) {
        for (StructureTypeEnum type : StructureTypeEnum.values()) {
            if (type.getValue().equals(inputValue)) {
                return type.name();
            }
        }
        throw new InvalidStructureTypeException(inputValue);
    }
}
