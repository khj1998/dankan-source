package com.dankan.exception.chatting;

import com.dankan.exception.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ChattingRoomDataNotExistException extends RuntimeException {
    private String message;
    private ErrorCode code;

    public ChattingRoomDataNotExistException(String roomId) {
        super(roomId);
        this.message = roomId;
    }
}
