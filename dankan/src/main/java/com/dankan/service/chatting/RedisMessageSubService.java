package com.dankan.service.chatting;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

public interface RedisMessageSubService extends MessageListener {
    void onMessage(Message message, byte[] pattern);
}