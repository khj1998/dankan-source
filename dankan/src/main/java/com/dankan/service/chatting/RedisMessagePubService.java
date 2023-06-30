package com.dankan.service.chatting;

import com.dankan.vo.ChattingMessage;

public interface RedisMessagePubService {
    public void publish(ChattingMessage chattingMessage);
}