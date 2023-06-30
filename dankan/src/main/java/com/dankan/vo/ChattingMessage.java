package com.dankan.vo;

import lombok.*;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@RedisHash
public class ChattingMessage implements Serializable {
    private String roomId;
    private String sender;
    private String message;
    private String imgUrl;
    private String memberId;
}