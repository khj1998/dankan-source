package com.dankan.service.login;

import com.dankan.dto.response.login.OauthLoginResponseDto;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class KakaoOauth implements SocialOauth {

    private final String kakaoBaseURL = "https://kauth.kakao.com/oauth/authorize";
    private final String kakaoBaseTokenURL = "https://kauth.kakao.com/oauth/token";

    @Value("${KAKAO_API_KEY}")
    private String kakaoApiKey;

    @Value("${KAKAO_REDIRECT_URL}")
    private String kakaoTokenRedirectURI;

    private final Gson gson;

    @Override
    public String getOauthRedirectURL() {
        Map<String, Object> params = new HashMap<>();
        params.put("client_id", kakaoApiKey);
        params.put("redirect_uri", kakaoTokenRedirectURI);
        params.put("response_type","code");
        params.put("scope","openid,account_email,profile_nickname,profile_image");

        String redirectURI = "?"+params.entrySet().stream()
                .map(x -> x.getKey() + "=" + x.getValue())
                .collect(Collectors.joining("&"));

        return kakaoBaseURL+redirectURI;
    }

    @Override
    public String getAccessToken(String code) {
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<MultiValueMap<String,String>> httpEntity = getHttpEntity(code);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(kakaoBaseTokenURL,httpEntity,String.class);

        if(responseEntity.getStatusCode() == HttpStatus.OK){
            return responseEntity.getBody();
        }

        return "카카오 로그인 실패";
    }

    @Override
    public OauthLoginResponseDto getLoginResponseDto(String idToken) {
        String payload = parseIdToken(idToken);
        JsonObject jsonObject = gson.fromJson(payload, JsonObject.class);

        return OauthLoginResponseDto.builder()
                .email(jsonObject.get("email").getAsString())
                .nickname(jsonObject.get("nickname").getAsString())
                .profileImg(jsonObject.get("picture").getAsString())
                .build();
    }

    private String parseIdToken(String jsonBody) {
        JsonObject jsonObject = gson.fromJson(jsonBody, JsonObject.class);
        String idToken = jsonObject.get("id_token").getAsString();

        String[] tokenParts = idToken.split("\\.");
        String paloadBase64Url = tokenParts[1];

        return new String(Base64.getUrlDecoder().decode(paloadBase64Url));
    }

    private HttpEntity<MultiValueMap<String,String>> getHttpEntity(String code) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("Accept","application/json");

        MultiValueMap<String,String> params = new LinkedMultiValueMap<>();
        params.add("grant_type","authorization_code");
        params.add("client_id",kakaoApiKey);
        params.add("redirect_uri",kakaoTokenRedirectURI);
        params.add("code",code);

        return new HttpEntity<>(params, headers);
    }
}
