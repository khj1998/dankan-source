package com.dankan.controller;

import com.dankan.service.chatting.ChattingService;
import com.dankan.service.chatting.RedisMessagePubService;
import com.dankan.vo.ChattingMessage;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chatting")
@RequiredArgsConstructor
@Slf4j
public class ChattingController {
    private final RedisMessagePubService redisMessagePubService;

    //Client 가 SEND 할 수 있는 경로
    //stompConfig 에서 설정한 applicationDestinationPrefixes 와 @MessageMapping 경로가 병합됨
    //"/pub/chat/chatting"
    @MessageMapping("/chatting-service")
    public void getChattingMessage(ChattingMessage chattingMessage) {
        redisMessagePubService.publish(chattingMessage);
    }
}
