package com.dankan.repository;

import com.dankan.domain.Room;
import com.dankan.repository.custom.RoomFilterCustomRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long>, RoomFilterCustomRepository {

    @Query(value = "select * from room r where r.address like %:address% and "
            +"r.is_tradeable = :isTradeable",nativeQuery = true)
    Slice<Room> findRoomByAddress(@Param("address") String address, @Param("isTradeable") Boolean isTradeable, Pageable pageable);

    @Query(value = "select * from room r where r.address = :address limit :limit",nativeQuery = true)
    Optional<Room> findByAddress(@Param("address") String address,@Param("limit") Long limit);

    @Query("select r from Room r where r.roomId = :roomId and  "
            +"r.isTradeable = :isTradeable")
    Optional<Room> findById(@Param("roomId") Long roomId,@Param("isTradeable") Boolean isTradeable);
}
