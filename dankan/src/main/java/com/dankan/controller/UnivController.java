package com.dankan.controller;

import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("univ")
@Api(tags = {"학교 관련 API"})
@AllArgsConstructor
public class UnivController {
    /**
     * TODO: 대학교 목록 조회 API
     * */
}
