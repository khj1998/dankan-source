package com.dankan.service.user;

import com.dankan.domain.User;
import com.dankan.dto.response.login.LoginResponseDto;
import com.dankan.dto.response.login.OauthLoginResponseDto;

import java.util.Optional;

public interface UserService {
    public boolean checkDuplicatedName(String name);
    public Optional<User> checkDuplicatedEmail(String name);
    public LoginResponseDto signUp(OauthLoginResponseDto oauthLoginResponseDto);
    public LoginResponseDto signIn(User user);
}
