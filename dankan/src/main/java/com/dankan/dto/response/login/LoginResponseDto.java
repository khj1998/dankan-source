package com.dankan.dto.response.login;

import com.dankan.domain.Token;
import com.dankan.domain.User;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class LoginResponseDto {
    private UUID id;
    private String accessToken;
    private String refreshToken;
    private String nickname;
    private String email;
    private String profilImg;
    private String phoneNum;
    private Boolean gender;
    private int userType;
    private String univEmail;

    public static LoginResponseDto of(User user, Token token) {
        return LoginResponseDto.builder()
                .id(user.getUserId())
                .accessToken(token.getAccessToken())
                .refreshToken(token.getRefreshToken())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .profilImg(user.getProfileImg())
                .phoneNum(user.getPhoneNum())
                .userType(user.getUserType())
                .build();
    }
}
