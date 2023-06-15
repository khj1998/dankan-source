package com.dankan.controller;

import com.dankan.exception.CommonResponse;
import com.dankan.exception.code.CommonCode;
import com.dankan.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("api/v1/user")
@Api(tags = {"사용자 api"})
@Slf4j
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @Operation(summary = "닉네임 중복 체크 api", description = "닉네임 중복 체크")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "닉네임 사용 가능"),
                    @ApiResponse(responseCode = "409", description = "닉네임 중복")
            }
    )
    @GetMapping("/nickname")
    public ResponseEntity<CommonResponse> checkDuplicatedNickname(@PathVariable String name) {
        return ResponseEntity.ok(CommonResponse.toResponse(CommonCode.OK, userService.checkDuplicatedName(name)));
    }
    /**
     * TODO: 이미지 수정 API
     * TODO: 대학교 이메일 인증 API
     * TODO: 사용자 정보 조회 API
     * */
}
