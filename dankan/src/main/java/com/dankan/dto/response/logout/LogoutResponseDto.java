package com.dankan.dto.response.logout;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LogoutResponseDto {
    private String accessToken;
}
