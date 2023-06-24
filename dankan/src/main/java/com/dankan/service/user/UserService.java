package com.dankan.service.user;

import com.dankan.domain.Authority;
import com.dankan.domain.User;
import com.dankan.dto.response.login.LoginResponseDto;
import com.dankan.dto.response.login.OauthLoginResponseDto;
import com.dankan.dto.response.logout.LogoutResponseDto;
import com.dankan.dto.response.user.UserResponseDto;

import java.util.List;
import java.util.Optional;

public interface UserService {
    public boolean checkDuplicatedName(String name);
    public UserResponseDto modifyNickName(String name);
    public UserResponseDto modifyProfileImg(String imgUrl);
    public Optional<User> checkDuplicatedEmail(String name);
    public LoginResponseDto signUp(OauthLoginResponseDto oauthLoginResponseDto);
    public LoginResponseDto signIn(User user);
    public UserResponseDto findUserByNickname();
    public List<UserResponseDto> findAll();
    public UserResponseDto findUserByNickname(String name);
    public void deleteUser();
    public void deleteUser(String name);
    public LogoutResponseDto logout();
    public List<Authority> getAuthorities();
}
