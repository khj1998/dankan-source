package com.dankan.service.post;

import com.dankan.domain.*;
import com.dankan.dto.request.post.PostHeartRequestDto;
import com.dankan.dto.request.post.PostRoomCreateRequestDto;
import com.dankan.dto.request.post.PostRoomEditRequestDto;
import com.dankan.dto.response.post.*;
import com.dankan.exception.post.PostNotFoundException;
import com.dankan.exception.room.RoomImageNotFoundException;
import com.dankan.exception.room.RoomNotFoundException;
import com.dankan.repository.*;
import com.dankan.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final RoomRepository roomRepository;
    private final PostHeartRepository postHeartRepository;
    private final RecentWatchRepository recentWatchRepository;
    private final RoomImageRepository roomImageRepository;

    private final Integer PAGING_COUNT = 5;
    private final Integer MAX_RECENT_WATCH_COUNT = 30;

    public PostServiceImpl(
            PostRepository postRepository,
            RoomRepository roomRepository,
            PostHeartRepository postHeartRepository,
            RecentWatchRepository recentWatchRepository,
            RoomImageRepository roomImageRepository) {
        this.postRepository = postRepository;
        this.roomRepository = roomRepository;
        this.postHeartRepository = postHeartRepository;
        this.recentWatchRepository = recentWatchRepository;
        this.roomImageRepository = roomImageRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public PostResponseDto getPostByRoomId(UUID roomId) {
        UUID userId = JwtUtil.getMemberId();
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RoomNotFoundException(roomId));
        Post post = postRepository.findByRoomId(roomId)
                .orElseThrow(() -> new PostNotFoundException(roomId));
        PostHeart postHeart = postHeartRepository.findByUserIdAndPostId(userId,post.getPostId());

        RoomImage roomImage = roomImageRepository.findByRoomIdAndImageType(room.getRoomId(),0L)
                .orElseThrow(() -> new RoomImageNotFoundException(room.getRoomId()));

        return PostResponseDto.of(post,room,postHeart,roomImage.getRoomImageUrl());
    }

    // 이미지 가져와야함
    @Override
    @Transactional(readOnly = true)
    public List<PostResponseDto> findRecentPost(Integer pages) {
        UUID userId = JwtUtil.getMemberId();
        List<PostResponseDto> responseDtoList = new ArrayList<>();
        Sort sort = Sort.by(Sort.Direction.DESC,"updatedAt");
        Pageable pageable = PageRequest.of(pages,PAGING_COUNT,sort);
        Slice<Post> postList = postRepository.findAll(pageable);

        for (Post post : postList) {
            Room room = roomRepository.findById(post.getRoomId())
                    .orElseThrow(() -> new RoomNotFoundException(post.getRoomId()));
            PostHeart postHeart = postHeartRepository.findByUserIdAndPostId(userId,post.getPostId());

            RoomImage roomImage = roomImageRepository.findByRoomIdAndImageType(room.getRoomId(),0L)
                    .orElseThrow(() -> new RoomImageNotFoundException(room.getRoomId()));

            PostResponseDto responseDto = PostResponseDto.of(post,room,postHeart,roomImage.getRoomImageUrl());
            responseDtoList.add(responseDto);
        }

        return responseDtoList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostResponseDto> findHeartPost(Integer pages) {
        List<PostResponseDto> postResponseDtoList = new ArrayList<>();
        UUID userId = JwtUtil.getMemberId();
        Pageable pageable = PageRequest.of(pages,PAGING_COUNT);
        List<PostHeart> postHeartList = postHeartRepository.findByUserId(userId,pageable);

        for (PostHeart postHeart : postHeartList) {
            Post post = postRepository.findById(postHeart.getPostId())
                    .orElseThrow(() -> new PostNotFoundException(postHeart.getPostId()));
            Room room = roomRepository.findById(post.getRoomId())
                    .orElseThrow(() -> new RoomNotFoundException(post.getRoomId()));

            RoomImage roomImage = roomImageRepository.findByRoomIdAndImageType(room.getRoomId(),0L)
                    .orElseThrow(() -> new RoomImageNotFoundException(room.getRoomId()));

            PostResponseDto postResponseDto = PostResponseDto.of(post,room,postHeart,roomImage.getRoomImageUrl());
            postResponseDtoList.add(postResponseDto);
        }

        return postResponseDtoList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostResponseDto> findMyPost(Integer pages) {
        List<PostResponseDto> postResponseDtoList = new ArrayList<>();
        UUID userId = JwtUtil.getMemberId();
        Pageable pageable = PageRequest.of(pages,PAGING_COUNT);
        List<Post> postList = postRepository.findByUserId(userId,pageable);

        for (Post post : postList) {
            Room room = roomRepository.findById(post.getRoomId())
                    .orElseThrow(() -> new RoomNotFoundException(post.getRoomId()));

            RoomImage roomImage = roomImageRepository.findByRoomIdAndImageType(room.getRoomId(),0L)
                    .orElseThrow(() -> new RoomImageNotFoundException(room.getRoomId()));

            PostResponseDto postResponseDto = PostResponseDto.of(post,room,roomImage.getRoomImageUrl());
            postResponseDtoList.add(postResponseDto);
        }

        return postResponseDtoList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostResponseDto> findRecentWatchPost(Integer pages) {
        List<PostResponseDto> postResponseDtoList = new ArrayList<>();
        UUID userId = JwtUtil.getMemberId();
        Sort sort = Sort.by(Sort.Direction.DESC,"createdAt");
        Pageable pageable = PageRequest.of(pages,PAGING_COUNT,sort);
        List<RecentWatchPost> recentWatchPostList = recentWatchRepository.findAllByUserId(userId,pageable);

        for (RecentWatchPost recentWatchPost : recentWatchPostList) {
            UUID postId = recentWatchPost.getPostId();
            Post post = postRepository.findById(postId)
                    .orElseThrow(() -> new PostNotFoundException(postId));

            Room room = roomRepository.findById(post.getRoomId())
                    .orElseThrow(() -> new RoomNotFoundException(post.getRoomId()));

            RoomImage roomImage = roomImageRepository.findByRoomIdAndImageType(room.getRoomId(),0L)
                    .orElseThrow(() -> new RoomImageNotFoundException(room.getRoomId()));

            PostResponseDto postResponseDto = PostResponseDto.of(post,room,roomImage.getRoomImageUrl());
            postResponseDtoList.add(postResponseDto);
        }

        return postResponseDtoList;
    }

    // 이미지 가져와야함
    @Override
    @Transactional
    public PostDetailResponseDto findPostDetail(UUID postId) {
        UUID userId = JwtUtil.getMemberId();
        Boolean isWatched = false;
        StringBuilder imgUrls = new StringBuilder();
        RoomImage roomImage;
        Long imageType;

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(postId));

        List<RecentWatchPost> recentWatchPostList = recentWatchRepository.findAllByUserId(userId);

        if (recentWatchPostList.size() >= MAX_RECENT_WATCH_COUNT) {
            recentWatchRepository.delete(recentWatchPostList.get(MAX_RECENT_WATCH_COUNT-1));
        }

        RecentWatchPost recentWatchPost = RecentWatchPost.of(userId,postId);

        for (RecentWatchPost watchedPost : recentWatchPostList) { // 이미 조회한 게시물 중복 체크
            if (watchedPost.getPostId() == postId) {
                isWatched = true;
                break;
            }
        }

        if (!isWatched) {
            recentWatchRepository.save(recentWatchPost);
        }

        PostHeart postHeart = postHeartRepository.findByUserIdAndPostId(userId,post.getPostId());
        Integer postHeartCount = postHeartRepository.findByPostId(post.getPostId()).size();

        Room room = roomRepository.findById(post.getRoomId())
                .orElseThrow(() -> new RoomNotFoundException(post.getRoomId()));

        for (imageType=0L;imageType<3L;imageType+=1) {
            roomImage = roomImageRepository.findByRoomIdAndImageType(room.getRoomId(),imageType)
                    .orElseThrow(() -> new RoomImageNotFoundException(room.getRoomId()));
            imgUrls.append(roomImage.getRoomImageUrl()).append(" ");
        }

        return PostDetailResponseDto.of(post,room,postHeart,postHeartCount, imgUrls.toString());
    }

    @Override
    @Transactional
    public PostCreateResponseDto addPost(PostRoomCreateRequestDto postRoomCreateRequestDto) {
        UUID userId = JwtUtil.getMemberId();

        Room room = Room.of(postRoomCreateRequestDto,userId);
        roomRepository.save(room);

        Post post = Post.of(postRoomCreateRequestDto,userId,room.getRoomId());

        postRepository.save(post);

        return PostCreateResponseDto.of(post,room);
    }

    @Override
    @Transactional
    public PostEditResponseDto editPost(PostRoomEditRequestDto postRoomEditRequestDto) {
        UUID userId = JwtUtil.getMemberId();
        Post post = postRepository.findByPostIdAndUserId(postRoomEditRequestDto.getPostId(), userId)
                        .orElseThrow(() -> new PostNotFoundException(postRoomEditRequestDto.getPostId()));
        post.setTitle(postRoomEditRequestDto.getTitle());
        post.setContent(postRoomEditRequestDto.getContent());
        postRepository.save(post);

        return PostEditResponseDto.of(post);
    }

    @Override
    @Transactional
    public void deletePost(UUID postId) {
        UUID userId = JwtUtil.getMemberId();
        Post post = postRepository.findByPostIdAndUserId(postId,userId)
                        .orElseThrow(() -> new PostNotFoundException(postId));
        postRepository.delete(post);
    }

    @Override
    @Transactional
    public PostHeartResponseDto heartPost(PostHeartRequestDto postHeartRequestDto) {
        UUID userId = JwtUtil.getMemberId();
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
