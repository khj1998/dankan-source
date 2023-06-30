package com.dankan.service.chatting;

import com.dankan.vo.ChattingMessage;
import com.dankan.vo.ChattingMessageResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisMessagePubServiceImpl implements RedisMessagePubService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final RedisMessageListenerContainer redisMessageListenerContainer;
    private final RedisMessageSubService redisMessageSubService;
    private final DynamoDBService dynamoDBService;

    @Override
    public void publish(ChattingMessage chattingMessage) {
        ChannelTopic topic = new ChannelTopic(chattingMessage.getRoomId());

        redisMessageListenerContainer.addMessageListener(redisMessageSubService, topic);

        ChattingMessageResponse response = ChattingMessageResponse.of(chattingMessage);

        dynamoDBService.save(response);

        redisTemplate.convertAndSend(topic.getTopic(), response);
    }
}
