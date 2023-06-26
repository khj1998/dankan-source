package com.dankan.repository;

import com.dankan.domain.Post;
import com.dankan.domain.RecentWatchPost;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RecentWatchRepository extends JpaRepository<RecentWatchPost, UUID> {
    List<RecentWatchPost> findAllByUserId(UUID userId);
    List<RecentWatchPost> findAllByUserId(UUID userId, Pageable pageable);
    List<RecentWatchPost> findAllByOrderByCreatedAtDesc();
}
