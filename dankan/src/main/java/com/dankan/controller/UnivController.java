package com.dankan.controller;

import com.dankan.dto.response.univ.UnivListResponseDto;
import com.dankan.dto.request.email.EmailCodeRequestDto;
import com.dankan.dto.request.email.EmailRequestDto;
import com.dankan.dto.response.univ.UnivListResponseDto;
import com.dankan.service.email.EmailService;
import com.dankan.service.email.EmailServiceImpl;
import com.dankan.service.univ.UnivService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("univ")
@Api(tags = {"학교 관련 API"})
@AllArgsConstructor
public class UnivController {
    private final UnivService univService;
    private final EmailService emailService;

    @Operation(summary = "대학교 리스트 조회 api", description = "사용자 리스트 조회")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "조회 환료")
            }
    )
    @GetMapping("/info")
    public ResponseEntity<List<UnivListResponseDto>> getUnivInfo() {
        return ResponseEntity.ok(univService.findAll());
    }

    @Operation(summary = "대학교 인증 메일 발송 api", description = "대학교 인증 메일 발송")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "발송 환료"),
                    @ApiResponse(responseCode = "403", description = "관리자 권한 없음"),
            }
    )
    @PostMapping("/mail")
    public ResponseEntity<String> mailConfirm(@RequestBody EmailRequestDto email) throws Exception {
        return ResponseEntity.ok(emailService.sendSimpleMessage(email.getEmail()));
    }

    @Operation(summary = "대학교 인증 코드 확인 api")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "발송 환료"),
                    @ApiResponse(responseCode = "403", description = "관리자 권한 없음"),
            }
    )
    @PostMapping("/verify-code")
    public ResponseEntity<Boolean> verifyEmailCode(@RequestBody EmailCodeRequestDto emailCodeRequestDto) {
        return ResponseEntity.ok(emailService.verifyCode(emailCodeRequestDto));
    }
}
