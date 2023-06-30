package com.dankan.service.chatting;

import com.dankan.domain.chat.Chatting;
import com.dankan.vo.ChattingMessageResponse;

import java.util.List;

public interface DynamoDBService {
    public Boolean createTable();
    public void save(ChattingMessageResponse chatting);
    public List<Chatting> findMessageHistory(Long id);
}
