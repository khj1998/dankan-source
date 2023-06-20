package com.dankan.controller;

import com.dankan.dto.response.login.TokenResponseDto;
import com.dankan.dto.response.user.UserResponseDto;
import com.dankan.repository.TokenRepository;
import com.dankan.service.token.TokenService;
import com.dankan.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/admin")
@Api(tags = {"관리자 전용 api"})
@Slf4j
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;
    private final TokenService tokenService;

    @Operation(summary = "특정 사용자 정보 api", description = "특정 사용자 정보 조회")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "정보 조회 완료"),
                    @ApiResponse(responseCode = "401", description = "토큰 만료"),
                    @ApiResponse(responseCode = "403", description = "관리자 권한 없음"),
                    @ApiResponse(responseCode = "404", description = "해당 멤버 없음"),
            }
    )
    @GetMapping("/user/info")
    public ResponseEntity<UserResponseDto> findUserByName(@RequestParam(value = "name") String name) {
        log.info("user name: {}", name);
        return ResponseEntity.ok(userService.findUserByNickname(name));
    }

    @Operation(summary = "모든 사용자 정보 api", description = "모든 사용자 정보 조회")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "정보 조회 완료"),
                    @ApiResponse(responseCode = "401", description = "토큰 만료"),
                    @ApiResponse(responseCode = "403", description = "관리자 권한 없음"),
                    @ApiResponse(responseCode = "404", description = "해당 멤버 없음"),
            }
    )
    @GetMapping("/user/whole-info")
    public ResponseEntity<List<UserResponseDto>> checkDuplicatedNickname() {
        return ResponseEntity.ok(userService.findAll());
    }

    @Operation(summary = "특정 사용자 토큰 정보 api", description = "특정 사용자 토큰 정보 조회")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "정보 조회 완료"),
                    @ApiResponse(responseCode = "401", description = "토큰 만료"),
                    @ApiResponse(responseCode = "403", description = "관리자 권한 없음"),
                    @ApiResponse(responseCode = "404", description = "해당 멤버 없음"),
            }
    )
    @GetMapping("/token/info")
    public ResponseEntity<TokenResponseDto> getTokenInfo(@RequestParam(value = "id") String id) {
        return ResponseEntity.ok(tokenService.findByUserId(UUID.fromString(id)));
    }

    @Operation(summary = "사용자 탈퇴 api", description = "사용자 회원 탈퇴")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "조회 환료"),
                    @ApiResponse(responseCode = "401", description = "토큰 만료"),
                    @ApiResponse(responseCode = "403", description = "관리자 권한 없음"),
                    @ApiResponse(responseCode = "404", description = "해당 멤버 없음"),
            }
    )
    @DeleteMapping("/user/delete")
    public ResponseEntity deleteUser(@PathParam(value = "name") String name) {
        userService.deleteUser(name);

        return ResponseEntity.ok().build();
    }
}
