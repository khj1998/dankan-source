package com.dankan.dto.resquest.token;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class TokenRequestDto {
    private UUID userId;
    private String refreshToken;
}
