package com.dankan.controller;

import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(("/auth"))
@Api(tags = {"권한 관련 api"})
@AllArgsConstructor
public class AuthController {
    /**
     * TODO: 권한 확인 API
     * */
}
