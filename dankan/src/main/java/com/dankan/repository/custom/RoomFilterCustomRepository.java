package com.dankan.repository.custom;

import com.dankan.domain.Room;
import com.dankan.dto.request.post.PostFilterRequestDto;

import java.util.List;

public interface RoomFilterCustomRepository {
    List<Room> findRoomByFilter(PostFilterRequestDto postFilterRequestDto);
}
