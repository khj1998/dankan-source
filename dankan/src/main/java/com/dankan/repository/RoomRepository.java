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

    Optional<Room> findFirstByRoomAddress_Address(@Param("address") String address);

    @Query("select r from Room r where r.roomId = :roomId and  "
            +"r.isTradeable = :isTradeable")
    Optional<Room> findById(@Param("roomId") Long roomId,@Param("isTradeable") Boolean isTradeable);
}
