package com.dankan.repository;

import com.dankan.domain.RoomReview;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<RoomReview, Long> {

    @Query("select r from RoomReview r where r.userId = :userId and "
          +"r.address = :address")
    Optional<RoomReview> findReview(@Param("userId") Long userId, @Param("address")String address);

    @Query("select r from RoomReview r where r.userId = :userId and " +
            "r.reviewId = :reviewId")
    Optional<RoomReview> findByUserIdAndReviewId(@Param("userId") Long userId
                                                ,@Param("reviewId") Long reviewId);

    @Query("select r from RoomReview r where r.address = :address")
    List<RoomReview> findByAddress(@Param("address") String address);

    @Query("select r from RoomReview r where r.address = :address")
    List<RoomReview> findByAddress(String address,Pageable pageable);

    @Query(value = "select * from review r where r.address like %:buildingName%",nativeQuery = true)
    List<RoomReview> findByBuildingSearch(@Param("buildingName") String buildingName);

    @Query(value = "select * from review r where r.address like %:address%",nativeQuery = true)
    List<RoomReview> findByAddressSearch(@Param("address") String address);
}
