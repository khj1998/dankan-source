package com.dankan.controller;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("api/v1/health")
@Api(tags = {"HEALTH CHECK"})
@Slf4j
public class UserController {
}
