package com.dankan.service.post;

import com.dankan.domain.PostHeart;
import com.dankan.domain.Post;
import com.dankan.domain.Room;
import com.dankan.dto.response.post.*;
import com.dankan.dto.request.post.PostHeartRequestDto;
import com.dankan.dto.request.post.PostRoomRequestDto;
import com.dankan.exception.post.PostNotFoundException;
import com.dankan.exception.room.RoomNotFoundException;
import com.dankan.repository.PostHeartRepository;
import com.dankan.repository.PostRepository;
import com.dankan.repository.RoomRepository;
import com.dankan.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final RoomRepository roomRepository;
    private final PostHeartRepository postHeartRepository;

    public PostServiceImpl(
            PostRepository postRepository,
            RoomRepository roomRepository,
            PostHeartRepository postHeartRepository) {
        this.postRepository = postRepository;
        this.roomRepository = roomRepository;
        this.postHeartRepository = postHeartRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public PostResponseDto getPostByRoomId(Long roomId) {
        Long userId = JwtUtil.getMemberId();
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RoomNotFoundException(roomId));
        Post post = postRepository.findByRoomId(roomId)
                .orElseThrow(() -> new PostNotFoundException(roomId));
        PostHeart postHeart = postHeartRepository.findByUserIdAndPostId(userId,post.getPostId());

        return PostResponseDto.of(post,room,postHeart);
    }

    // 이미지 가져와야함
    @Override
    @Transactional(readOnly = true)
    public List<PostResponseDto> findRecentPost(Integer pages) {
        Long userId = JwtUtil.getMemberId();
        List<PostResponseDto> responseDtoList = new ArrayList<>();
        Sort sort = Sort.by(Sort.Direction.DESC,"updatedAt");
        Pageable pageable = PageRequest.of(pages,5,sort);
        Slice<Post> postList = postRepository.findAll(pageable);

        for (Post post : postList) {
            Room room = roomRepository.findById(post.getRoomId())
                    .orElseThrow(() -> new RoomNotFoundException(post.getRoomId()));
            PostHeart postHeart = postHeartRepository.findByUserIdAndPostId(userId,post.getPostId());
            PostResponseDto responseDto = PostResponseDto.of(post,room,postHeart);
            responseDtoList.add(responseDto);
        }

        return responseDtoList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostResponseDto> findHeartPost(Integer pages) {
        List<PostResponseDto> postResponseDtoList = new ArrayList<>();
        Long userId = JwtUtil.getMemberId();
        Pageable pageable = PageRequest.of(pages,5);
        List<PostHeart> postHeartList = postHeartRepository.findByUserId(userId,pageable);

        for (PostHeart postHeart : postHeartList) {
            Post post = postRepository.findById(postHeart.getPostId())
                    .orElseThrow(() -> new PostNotFoundException(postHeart.getPostId()));
            Room room = roomRepository.findById(post.getRoomId())
                    .orElseThrow(() -> new RoomNotFoundException(post.getRoomId()));
            PostResponseDto postResponseDto = PostResponseDto.of(post,room,postHeart);
            postResponseDtoList.add(postResponseDto);
        }

        return postResponseDtoList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostResponseDto> findMyPost(Integer pages) {
        List<PostResponseDto> postResponseDtoList = new ArrayList<>();
        Long userId = JwtUtil.getMemberId();
        Pageable pageable = PageRequest.of(pages,5);
        List<Post> postList = postRepository.findByUserId(userId,pageable);

        for (Post post : postList) {
            Room room = roomRepository.findById(post.getRoomId())
                    .orElseThrow(() -> new RoomNotFoundException(post.getRoomId()));
            PostResponseDto postResponseDto = PostResponseDto.of(post,room);
            postResponseDtoList.add(postResponseDto);
        }

        return postResponseDtoList;
    }

    // 이미지 가져와야함
    @Override
    @Transactional(readOnly = true)
    public PostDetailResponseDto findPostDetail(Long postId) {
        Long userId = JwtUtil.getMemberId();
        Integer postHeartCount;

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(postId));

        PostHeart postHeart = postHeartRepository.findByUserIdAndPostId(userId,post.getPostId());
        postHeartCount = postHeartRepository.findByPostId(post.getPostId()).size();

        Room room = roomRepository.findById(post.getRoomId())
                .orElseThrow(() -> new RoomNotFoundException(post.getRoomId()));

        return PostDetailResponseDto.of(post,room,postHeart,postHeartCount);
    }

    @Override
    @Transactional
    public PostCreateResponseDto addPost(PostRoomRequestDto postRoomRequestDto) {
        Long userId = JwtUtil.getMemberId();
        Room room = Room.of(postRoomRequestDto,userId);
        roomRepository.save(room);

        Post post = Post.of(postRoomRequestDto,userId,room.getRoomId());
        postRepository.save(post);

        return PostCreateResponseDto.of(post,room);
    }

    @Override
    @Transactional
    public PostEditResponseDto editPost(PostRoomRequestDto postRoomRequestDto) {
        Long userId = JwtUtil.getMemberId();
        Post post = postRepository.findByPostIdAndUserId(postRoomRequestDto.getPostId(), userId)
                        .orElseThrow(() -> new PostNotFoundException(postRoomRequestDto.getPostId()));
        post.setTitle(postRoomRequestDto.getTitle());
        post.setContent(postRoomRequestDto.getContent());
        postRepository.save(post);

        return PostEditResponseDto.of(post);
    }

    @Override
    @Transactional
    public void deletePost(Long postId) {
        Long userId = JwtUtil.getMemberId();
        Post post = postRepository.findByPostIdAndUserId(postId,userId)
                        .orElseThrow(() -> new PostNotFoundException(postId));
        postRepository.delete(post);
    }

    @Override
    @Transactional
    public PostHeartResponseDto heartPost(PostHeartRequestDto postHeartRequestDto) {
        Long userId = JwtUtil.getMemberId();
        PostHeart postHeart = postHeartRepository.findByUserIdAndPostId(userId,postHeartRequestDto.getPostId());

        if (postHeart == null) {
            postHeart = PostHeart.builder()
                    .postId(postHeartRequestDto.getPostId())
                    .userId(userId)
                    .build();
            postHeartRepository.save(postHeart);
        } else {
            postHeartRepository.deleteById(postHeart.getPostHeartId());
        }

        return PostHeartResponseDto.of(postHeart.getPostId(),userId);
    }
}
