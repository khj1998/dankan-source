package com.dankan.controller;

import com.dankan.domain.User;
import com.dankan.dto.response.login.LoginResponseDto;
import com.dankan.dto.response.login.OauthLoginResponseDto;
import com.dankan.service.login.OAuthService;
import com.dankan.service.login.SocialLoginType;
import com.dankan.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping(value = "/login")
@RequiredArgsConstructor
public class OauthController {
    private final OAuthService oauthService;
    private final UserService userService;

    @GetMapping(value = "/{socialLoginType}")
    public void socialLoginType(
            @PathVariable(name = "socialLoginType") SocialLoginType socialLoginType) {
        log.info(">> 사용자로부터 SNS 로그인 요청을 받음 :: {} Social Login", socialLoginType);
        oauthService.request(socialLoginType);
    }

    @GetMapping(value = "/{socialLoginType}/token")
    public ResponseEntity<LoginResponseDto> codeCallBack(
            @PathVariable(name = "socialLoginType") SocialLoginType socialLoginType,
            @RequestParam(name = "code") String code) {
        OauthLoginResponseDto oauthLoginResponseDto = oauthService.getLoginResponseDto(socialLoginType,code);
        User user = userService.checkDuplicatedEmail(oauthLoginResponseDto.getEmail());
        LoginResponseDto loginResponseDto = (user!=null ? userService.signIn(user) : userService.signUp(oauthLoginResponseDto));

        return ResponseEntity.ok(loginResponseDto);
    }
}
