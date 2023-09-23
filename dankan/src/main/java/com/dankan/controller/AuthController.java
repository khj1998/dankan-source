package com.dankan.controller;

import com.dankan.domain.Authority;
import com.dankan.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(("/auth"))
@Api(tags = {"권한 관련 api"})
@AllArgsConstructor
public class AuthController {
    private final UserService userService;

    @GetMapping("check")
    @ApiOperation(value = "사용자가 가진 권한 확인")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 환료")
    })
    public ResponseEntity<List<Authority>> getAuthorities() {
        return ResponseEntity.ok(userService.getAuthorities());
    }
}
