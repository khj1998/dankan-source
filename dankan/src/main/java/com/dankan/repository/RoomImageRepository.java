package com.dankan.repository;

import com.dankan.domain.RoomImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoomImageRepository extends JpaRepository<RoomImage, UUID> {
    Optional<RoomImage> findByRoomIdAndImageType(UUID roomId,Long imageType);
}
