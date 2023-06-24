package com.dankan.repository;

import com.dankan.domain.PostHeart;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PostHeartRepository extends JpaRepository<PostHeart, UUID> {
    PostHeart findByUserIdAndPostId(UUID userId, UUID postId);
    List<PostHeart> findByPostId(UUID postId);
    List<PostHeart> findByUserId(UUID userId, Pageable pageable);
}
