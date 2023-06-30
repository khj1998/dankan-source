package com.dankan.enum_converter;

import com.dankan.exception.type.InvalidOptionTypeException;
import com.dankan.exception.type.InvalidStructureTypeException;
import lombok.Getter;

@Getter
public enum StructureTypeEnum {
    분리형(0L),
    오픈형(1L),
    복층형(2L),
    직접입력(3L),
    해당없음(4L);

    private Long value;

    StructureTypeEnum(Long value) {
        this.value = value;
    }

    public static Long getStructureTypeValue(String inputType) {
        for (StructureTypeEnum type : StructureTypeEnum.values()) {
            if (type.name().equals(inputType)) {
                return type.getValue();
            }
        }
        throw new InvalidStructureTypeException(inputType);
    }

    public static String getStructureTypeName(Long inputValue) {
        for (StructureTypeEnum type : StructureTypeEnum.values()) {
            if (type.getValue().equals(inputValue)) {
                return type.name();
            }
        }
        throw new InvalidStructureTypeException(inputValue.toString());
    }
}
