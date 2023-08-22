package com.dankan.controller;

import com.dankan.domain.chat.Chatting;
import com.dankan.dto.response.chatting.ChattingLogResponseDto;
import com.dankan.dto.response.login.TokenResponseDto;
import com.dankan.dto.response.report.ReportResponseDto;
import com.dankan.dto.response.user.UserResponseDto;
import com.dankan.service.chatting.ChattingService;
import com.dankan.service.chatting.DynamoDBService;
import com.dankan.service.report.ReportService;
import com.dankan.service.token.TokenService;
import com.dankan.service.univ.user.UserService;
import com.dankan.vo.UserInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
@Api(tags = {"관리자 전용 api"})
@Slf4j
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;
    private final TokenService tokenService;
    private final ReportService reportService;
    private final ChattingService chattingService;
    private final DynamoDBService dynamoDBService;

    @Operation(summary = "특정 사용자 정보 api", description = "특정 사용자 정보 조회")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "정보 조회 완료"),
                    @ApiResponse(responseCode = "403", description = "관리자 권한 없음"),
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
                    @ApiResponse(responseCode = "403", description = "관리자 권한 없음"),
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
                    @ApiResponse(responseCode = "403", description = "관리자 권한 없음"),
            }
    )
    @GetMapping("/token/info")
    public ResponseEntity<TokenResponseDto> getTokenInfo(@RequestParam(value = "id") Long id) {
        return ResponseEntity.ok(tokenService.findByUserId(id));
    }

    @Operation(summary = "사용자 탈퇴 api", description = "사용자 회원 탈퇴")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "조회 환료"),
                    @ApiResponse(responseCode = "403", description = "관리자 권한 없음"),
            }
    )
    @DeleteMapping("/user/delete")
    public ResponseEntity deleteUser(@PathParam(value = "name") String name) {
        userService.deleteUser(name);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "특정 게시물 신고 조회 api",description = "특정 게시물 신고 조회")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "특정 게시물 신고 조회 완료"),
                    @ApiResponse(responseCode = "403", description = "관리자 권한 없음"),
            }
    )
    @GetMapping("/post-report/find")
    public ResponseEntity<ReportResponseDto> findPostReport(@RequestParam("reportId") Long reportId) {
        ReportResponseDto reportResponseDto = reportService.findPostReport(reportId);
        return ResponseEntity.ok(reportResponseDto);
    }

    @Operation(summary = "특정 게시물 신고 삭제 api",description = "특정 게시물 신고 삭제")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "특정 게시물 신고 삭제 완료"),
                    @ApiResponse(responseCode = "403", description = "관리자 권한 없음"),
            }
    )
    @DeleteMapping("/post-report/remove")
    public ResponseEntity removePostReport(@RequestParam("reportId") Long reportId) {
        reportService.removePostReport(reportId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "특정 리뷰 신고 조회 api",description = "특정 리뷰 신고 조회")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "특정 리뷰 신고 조회 완료"),
                    @ApiResponse(responseCode = "403", description = "관리자 권한 없음"),
            }
    )
    @GetMapping("/review-report/find")
    public ResponseEntity<ReportResponseDto> findReviewReport(@RequestParam("reportId") Long reportId) {
        ReportResponseDto reportResponseDto = reportService.findReviewReport(reportId);
        return ResponseEntity.ok(reportResponseDto);
    }
    
    @Operation(summary = "특정 리뷰 신고 삭제 api",description = "특정 리뷰 신고 삭제")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "특정 리뷰 신고 삭제 완료"),
                    @ApiResponse(responseCode = "403", description = "관리자 권한 없음"),
            }
    )
    @DeleteMapping("/review-report/remove")
    public ResponseEntity removeReviewReport(@RequestParam("reportId") Long  reportId) {
        reportService.removeReviewReport(reportId);
        return ResponseEntity.ok().build();
    }

    @ApiOperation("채팅 기록 조회 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "기록 조회 성공 "),
            @ApiResponse(responseCode = "403",description = "관리자 권한이 없음"),
    })
    @GetMapping("/chatting/log")
    public ResponseEntity<List<ChattingLogResponseDto>> getChattingLog(@RequestParam("roomId") Long id) {
        final List<Chatting> chattings = dynamoDBService.findMessageHistory(id);
        List<ChattingLogResponseDto> list = new ArrayList<>();

        for(Chatting chatting : chattings) {
            UserInfo info = chattingService.getInfo(Long.parseLong(chatting.getMemberId()));

            list.add(
                    ChattingLogResponseDto.builder()
                            .roomId(chatting.getRoomId())
                            .message(chatting.getMessage())
                            .publishedAt(chatting.getPublishedAt())
                            .senderName(info.getNickname())
                            .imgUrl(info.getProfileImg())
                            .build()
            );
        }

        return ResponseEntity.ok(list);
    }
}
