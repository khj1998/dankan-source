package com.dankan.service.chatting;

import com.dankan.dto.response.chatting.ChattingLogResponseDto;

import java.util.List;

public interface ChattingService {
    public List<ChattingLogResponseDto> getHistory(Long id);
}
