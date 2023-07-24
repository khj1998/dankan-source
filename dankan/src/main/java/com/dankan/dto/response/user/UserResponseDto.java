package com.dankan.dto.response.user;

import com.dankan.domain.User;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class UserResponseDto {
    private Long userId;
    private String email;
    private String nickname;
    private Boolean gender;
    private String phoneNumber;
    private String profileImg;
    private String univEmail;

    public static UserResponseDto of(User user) {
        return UserResponseDto.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .gender(user.getGender())
                .phoneNumber(user.getPhoneNum())
                .profileImg(user.getProfileImg())
                .univEmail(user.getUnivEmail())
                .build();
    }
}
