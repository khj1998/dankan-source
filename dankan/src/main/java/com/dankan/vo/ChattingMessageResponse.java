package com.dankan.vo;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class ChattingMessageResponse {
    private String roomId;
    private String sender;
    private String message;
    private String imgUrl;
    private String memberId;
    private String publishedAt;

    public static ChattingMessageResponse of(ChattingMessage chattingMessage) {
        return ChattingMessageResponse.builder()
                .roomId(chattingMessage.getRoomId())
                .sender(chattingMessage.getSender())
                .memberId(chattingMessage.getMemberId())
                .message(chattingMessage.getMessage())
                .imgUrl(chattingMessage.getImgUrl())
                .publishedAt(LocalDateTime.now().toString())
                .build();
    }
}
