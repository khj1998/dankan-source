package com.dankan.controller;

import com.dankan.dto.response.univ.UnivListResponseDto;
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
}
