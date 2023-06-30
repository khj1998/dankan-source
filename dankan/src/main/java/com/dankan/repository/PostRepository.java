package com.dankan.repository;

import com.dankan.domain.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Post> findByPostIdAndUserId(Long postId, Long userId);
    Optional<Post> findByRoomId(Long roomId);
    List<Post> findByUserId(Long userId, Pageable pageable);
}
