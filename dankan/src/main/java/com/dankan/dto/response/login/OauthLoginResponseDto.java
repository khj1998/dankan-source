package com.dankan.dto.response.login;

import com.dankan.domain.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OauthLoginResponseDto {
    private String nickname;
    private String email;
    private String profileImg;
    private String phoneNum;
    private Boolean gender;
    private String univEmail;

    public static OauthLoginResponseDto of(User user) {
        return OauthLoginResponseDto.builder()
                .nickname(user.getNickname())
                .email(user.getEmail())
                .profileImg(user.getProfileImg())
                .phoneNum(user.getPhoneNum())
                .gender(user.getGender())
                .univEmail(user.getUnivEmail())
                .build();
    }
}
