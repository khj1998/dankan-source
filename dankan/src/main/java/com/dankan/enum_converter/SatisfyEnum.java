package com.dankan.enum_converter;

import com.dankan.exception.type.InvalidSatisfyException;
import lombok.Getter;

@Getter
public enum SatisfyEnum {

    AccessRate2(5L),
    AccessRate1(3L),
    AccessRate0(1L),
    CleanRate2(5L),
    CleanRate1(3L),
    CleanRate0(1L),
    FacilityRate2(5L),
    FacilityRate1(3L),
    FacilityRate0(1L),
    HostRate2(5L),
    HostRate1(3L),
    HostRate0(1L),
    NoiseRate2(5L),
    NoiseRate1(3L),
    NoiseRate0(1L);

    private final Long value;

    SatisfyEnum(Long value) {
        this.value = value;
    }

    public static Long getSatisfyValue(String inputType) {
        for (SatisfyEnum type : SatisfyEnum.values()) {
            if (type.name().equals(inputType)) {
                return type.getValue();
            }
        }
        throw new InvalidSatisfyException(inputType);
    }
}
