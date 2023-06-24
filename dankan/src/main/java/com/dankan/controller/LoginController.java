package com.dankan.controller;

import com.dankan.domain.User;
import com.dankan.dto.response.login.LoginResponseDto;
import com.dankan.dto.response.login.OauthLoginResponseDto;
import com.dankan.service.login.OAuthService;
import com.dankan.service.login.SocialLoginType;
import com.dankan.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping(value = "/login")
@Api(tags = {"로그인 API"})
@RequiredArgsConstructor
public class LoginController {
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

        Optional<User> result = userService.checkDuplicatedEmail(oauthLoginResponseDto.getEmail());

        if(result.isEmpty()) {
            return ResponseEntity.ok(userService.signUp(oauthLoginResponseDto));
        }
        else
        {
            return ResponseEntity.ok(userService.signIn(result.get()));
        }
    }
}
