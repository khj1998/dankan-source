package com.dankan.dto.response.chatting;

import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class ChattingLogResponseDto {
    private String roomId;
    private String senderName;
    private String message;
    private String imgUrl;
    private String publishedAt;
}
