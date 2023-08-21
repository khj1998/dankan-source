package com.dankan.dto.response.login;

import com.dankan.domain.Token;
import com.dankan.domain.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginResponseDto {
    private Long id;
    private String accessToken;
    private String refreshToken;
    private String nickname;
    private String email;
    private String profileImg;
    private String phoneNum;
    private Boolean gender;
    private Long userType;
    private String univEmail;
    private Boolean isExistedUser;

    public static LoginResponseDto of(User user, Token token,Boolean isExistedUser) {
        return LoginResponseDto.builder()
                .id(user.getUserId())
                .accessToken(token.getAccessToken())
                .refreshToken(token.getRefreshToken())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .profileImg(user.getProfileImg())
                .phoneNum(user.getPhoneNum())
                .userType(user.getUserType())
                .isExistedUser(isExistedUser)
                .build();
    }
}
