package com.dankan.repository;

import com.dankan.domain.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("select p from Post p where p.postId = :postId and "
          +"p.userId = :userId")
    Optional<Post> findByPostIdAndUserId(@Param("postId") Long postId,@Param("userId") Long userId);

    @Query("select p from Post p where p.roomId = :roomId and p.isShown = :isShown")
    Optional<Post> findByRoomId(@Param("roomId") Long roomId,@Param("isShown") Boolean isShown);

    @Query("select p from Post p where p.userId = :userId and "
        +"p.isShown = :isShown")
    List<Post> findByUserId(@Param("userId") Long userId,@Param("isShown") Boolean isShown , Pageable pageable);

    @Query("select p from Post p where p.isShown = :isShown")
    Slice<Post> findAllPost(@Param("isShown") Boolean isShown, Pageable pageable);
}
