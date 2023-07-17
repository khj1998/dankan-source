package com.dankan.repository;

import com.dankan.domain.Room;
import com.dankan.repository.custom.RoomFilterCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long>, RoomFilterCustomRepository {
}
