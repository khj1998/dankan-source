package com.dankan.repository;

import com.dankan.domain.PostHeart;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PostHeartRepository extends JpaRepository<PostHeart, Long> {
    PostHeart findByUserIdAndPostId(Long userId, Long postId);
    List<PostHeart> findByPostId(Long postId);
    List<PostHeart> findByUserId(Long userId, Pageable pageable);
}
