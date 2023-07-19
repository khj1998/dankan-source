package com.dankan.repository;

import com.dankan.domain.Room;
import com.dankan.repository.custom.RoomFilterCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long>, RoomFilterCustomRepository {
    Optional<Room> findFirstByRoomAddress_Address(String address);
}
