package com.dankan.service.token;

import com.dankan.dto.response.login.TokenResponseDto;
import com.dankan.dto.resquest.token.TokenRequestDto;

public interface TokenService {
    public Boolean isExpired();
    public TokenResponseDto reissueAccessToken(TokenRequestDto tokenRequestDto);
}
