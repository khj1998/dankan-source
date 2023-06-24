package com.dankan.repository;

import com.dankan.domain.RoomImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RoomImageRepository extends JpaRepository<RoomImage, UUID> {
}
