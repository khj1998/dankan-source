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
public class GoogleOauth implements SocialOauth {
    private final String googleBaseURL = "https://accounts.google.com/o/oauth2/v2/auth";
    private final String googleBaseTokenURL = "https://oauth2.googleapis.com/token";

    @Value("${GOOGLE_CLIENT_ID}")
    private String googleClientId;

    @Value("${GOOGLE_CLIENT_SECRET}")
    private String googleClientSecret;

    @Value("${GOOGLE_REDIRECT_URL}")
    private String googleTokenRedirectURI;

    private final Gson gson;

    @Override
    public String getOauthRedirectURL() {
        Map<String, Object> params = new HashMap<>();
        params.put("client_id", googleClientId);
        params.put("redirect_uri", googleTokenRedirectURI);
        params.put("response_type","code");
        params.put("scope","openid https://www.googleapis.com/auth/userinfo.email");

        String redirectURI = "?"+params.entrySet().stream()
                .map(x -> x.getKey() + "=" + x.getValue())
                .collect(Collectors.joining("&"));

        return googleBaseURL+redirectURI;
    }

    @Override
    public String getAccessToken(String code) {
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<MultiValueMap<String,String>> httpEntity = getHttpEntity(code);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(googleBaseTokenURL,httpEntity,String.class);

        if(responseEntity.getStatusCode() == HttpStatus.OK){
            return responseEntity.getBody();
        }

        return "구글 로그인 실패";
    }

    @Override
    public OauthLoginResponseDto getLoginResponseDto(String idToken) {
        String payload = parseIdToken(idToken);
        JsonObject jsonObject = gson.fromJson(payload, JsonObject.class);

        return OauthLoginResponseDto.builder()
                .email(jsonObject.get("email").getAsString())
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
        params.add("client_id",googleClientId);
        params.add("client_secret",googleClientSecret);
        params.add("redirect_uri",googleTokenRedirectURI);
        params.add("code",code);

        return new HttpEntity<>(params, headers);
    }
}
