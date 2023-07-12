package com.dankan.repository;

import com.dankan.domain.Post;
import com.dankan.domain.RecentWatchPost;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RecentWatchRepository extends JpaRepository<RecentWatchPost, UUID> {
    List<RecentWatchPost> findAllByUserId(Long userId);
    List<RecentWatchPost> findAllByUserId(Long userId, Pageable pageable);
    List<RecentWatchPost> findAllByOrderByUpdatedAtDesc();

    @Modifying
    @Query("update RecentWatchPost rp set rp.updatedAt = :updatedAt where rp.postId = :postId")
    void updateDate(@Param("updatedAt") LocalDate updatedAt, @Param("postId") Long postId);
}
