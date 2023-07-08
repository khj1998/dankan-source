package com.dankan.repository;

import com.dankan.domain.RoomReview;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReviewRepository extends JpaRepository<RoomReview, Long> {

    @Query("select r from RoomReview r where r.userId = :userId and "
          +"r.address = :address")
    Optional<RoomReview> findReview(@Param("userId") Long userId, @Param("address")String address);
    Optional<RoomReview> findByUserIdAndReviewId(Long userId,Long reviewId);
    List<RoomReview> findByAddress(String address);
    List<RoomReview> findByAddress(String address,Pageable pageable);
}
