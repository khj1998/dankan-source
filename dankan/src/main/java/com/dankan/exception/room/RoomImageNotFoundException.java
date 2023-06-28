package com.dankan.exception.room;

import com.dankan.exception.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@Getter
@ResponseStatus(HttpStatus.NOT_FOUND)
public class RoomImageNotFoundException extends RuntimeException {
    private String message;
    private ErrorCode errorCode;

    public RoomImageNotFoundException(Long roomId) {
        super(roomId.toString());
        this.message = roomId.toString();
    }
}
