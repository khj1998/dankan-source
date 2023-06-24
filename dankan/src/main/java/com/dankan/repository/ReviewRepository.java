package com.dankan.repository;

import com.dankan.domain.RoomReview;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReviewRepository extends JpaRepository<RoomReview, UUID> {
    Optional<RoomReview> findByUserIdAndReviewId(UUID userId,UUID reviewId);
    List<RoomReview> findByAddress(String address);
    List<RoomReview> findByAddress(String address,Pageable pageable);
}