package com.dankan.dto.request.email;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailCodeRequestDto {
    private String email;
    private String code;
}
