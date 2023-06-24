package com.dankan.exception.room;

import com.dankan.exception.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@Getter
@ResponseStatus(HttpStatus.NOT_FOUND)
public class RoomNotFoundException extends RuntimeException {
    private String message;
    private ErrorCode code;

    public RoomNotFoundException(UUID roomId) {
        super(roomId.toString());
        this.message = roomId.toString();
    }

    public RoomNotFoundException(String address) {
        super(address);
        this.message = address;
    }
}
