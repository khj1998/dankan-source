package com.dankan.controller;

import com.dankan.dto.request.certification.SendMessageRequestDto;
import com.dankan.dto.request.sns.CertificationRequestDto;
import com.dankan.dto.response.logout.LogoutResponseDto;
import com.dankan.dto.response.user.UserResponseDto;
import com.dankan.service.s3.S3UploadService;
import com.dankan.service.sms.SmsService;
import com.dankan.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/user")
@Api(tags = {"사용자 관련 api"})
@Slf4j
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final S3UploadService s3UploadService;
    private final SmsService smsService;

    @Operation(summary = "닉네임 중복 체크 api", description = "닉네임 중복 체크")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "닉네임 사용 가능")
            }
    )
    @GetMapping("/nickname")
    public ResponseEntity<Boolean> checkDuplicatedNickname(@RequestParam(value = "name") String name) {
        return ResponseEntity.ok(userService.checkDuplicatedName(name));
    }

    @Operation(summary = "닉네임 변경 api", description = "닉네임 변경")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "닉네임 변경 완료")
            }
    )
    @GetMapping("/modify-nickname")
    public ResponseEntity<UserResponseDto> modifyNickName(@RequestParam(value = "nickname") String nickname) {
        return ResponseEntity.ok(userService.modifyNickName(nickname));
    }

    @Operation(summary = "프로필 이미지 api", description = "프로필 이미지 변경")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "프로필 이미지 변경 완료")
            }
    )
    @PostMapping(value = "/profileImg", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UserResponseDto> modifyProfileImg(@RequestParam(value = "image") MultipartFile multipartFile) throws IOException {
        String imgUrl = s3UploadService.upload(multipartFile, "profile");

        return ResponseEntity.ok(userService.modifyProfileImg(imgUrl));
    }

    @Operation(summary = "사용자 정보 조회 api", description = "사용자 정보 조회")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "조회 환료")
            }
    )
    @GetMapping("/info")
    public ResponseEntity<UserResponseDto> getUserInfo() {
        return ResponseEntity.ok(userService.findUserByNickname());
    }

    @Operation(summary = "사용자 탈퇴 api", description = "사용자 회원 탈퇴")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "탈퇴 환료")
            }
    )
    @DeleteMapping("/delete")
    public ResponseEntity deleteUser() {
        userService.deleteUser();

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "로그아웃 api", description = "로그아웃")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "로그아웃 환료")
            }
    )
    @GetMapping("/logout")
    public ResponseEntity<LogoutResponseDto> logout() {
        return ResponseEntity.ok(userService.logout());
    }


    @Operation(summary = "핸드폰 본인 인증 문자 발송 api", description = "핸드폰 본인 인증 문자 발송")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "본인 인증 메시지 보내기 환료"),
                    @ApiResponse(responseCode = "403", description = "관리자 권한 없음"),
            }
    )
    @PostMapping("/message")
    public ResponseEntity sendIdentifyMessage(@RequestBody SendMessageRequestDto SendMessageRequestDto) {
        smsService.sendMessage(SendMessageRequestDto.getPhoneNumber());

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "사용자 핸드폰 인증 api", description = "사용자 핸드폰 인증")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "핸드폰 인증 환료"),
                    @ApiResponse(responseCode = "403", description = "관리자 권한 없음"),
            }
    )
    @PostMapping("/verify")
    public ResponseEntity<Boolean> verifyUser(@RequestBody CertificationRequestDto certificationRequestDto) {
        return ResponseEntity.ok(smsService.verifyNumber(certificationRequestDto));
    }
}
