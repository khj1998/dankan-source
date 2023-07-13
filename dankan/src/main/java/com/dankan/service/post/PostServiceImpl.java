package com.dankan.service.post;

import com.dankan.domain.*;
import com.dankan.dto.request.post.PostRoomEditRequestDto;
import com.dankan.dto.response.post.*;
import com.dankan.dto.request.post.PostHeartRequestDto;
import com.dankan.dto.request.post.PostRoomRequestDto;
import com.dankan.exception.datelog.DateLogNotFoundException;
import com.dankan.exception.image.ImageNotFoundException;
import com.dankan.exception.post.PostNotFoundException;
import com.dankan.exception.room.RoomNotFoundException;
import com.dankan.repository.*;
import com.dankan.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final RoomRepository roomRepository;
    private final PostHeartRepository postHeartRepository;
    private final DateLogRepository dateLogRepository;
    private final RecentWatchRepository recentWatchRepository;
    private final OptionsRepository optionsRepository;
    private final ImageRepository imageRepository;


    public PostServiceImpl(
            PostRepository postRepository,
            RoomRepository roomRepository,
            PostHeartRepository postHeartRepository,
            DateLogRepository dateLogRepository,
            RecentWatchRepository recentWatchRepository,
            OptionsRepository optionsRepository,
            ImageRepository imageRepository) {
        this.postRepository = postRepository;
        this.roomRepository = roomRepository;
        this.postHeartRepository = postHeartRepository;
        this.recentWatchRepository = recentWatchRepository;
        this.dateLogRepository = dateLogRepository;
        this.optionsRepository = optionsRepository;
        this.imageRepository = imageRepository;
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
        List<Options> optionsList = optionsRepository.findByRoomId(room.getRoomId());

        Image roomImage = imageRepository.findMainImage(room.getRoomId(),0L)
                .orElseThrow(() -> new ImageNotFoundException(room.getRoomId()));

        return PostResponseDto.of(post,room,postHeart,roomImage.getImageUrl(),optionsList);
    }

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
            List<Options> optionsList = optionsRepository.findByRoomId(room.getRoomId());

            Image roomImage = imageRepository.findMainImage(room.getRoomId(),0L)
                    .orElseThrow(() -> new ImageNotFoundException(room.getRoomId()));

            PostResponseDto responseDto = PostResponseDto.of(post,room,postHeart,roomImage.getImageUrl(),optionsList);
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
            List<Options> optionsList = optionsRepository.findByRoomId(room.getRoomId());

            Image roomImage = imageRepository.findMainImage(room.getRoomId(),0L)
                    .orElseThrow(() -> new ImageNotFoundException(room.getRoomId()));

            PostResponseDto postResponseDto = PostResponseDto.of(post,room,postHeart,roomImage.getImageUrl(),optionsList);
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
            List<Options> optionsList = optionsRepository.findByRoomId(room.getRoomId());

            Image roomImage = imageRepository.findMainImage(room.getRoomId(),0L)
                    .orElseThrow(() -> new ImageNotFoundException(room.getRoomId()));

            PostResponseDto postResponseDto = PostResponseDto.of(post,room,roomImage.getImageUrl(),optionsList);
            postResponseDtoList.add(postResponseDto);
        }

        return postResponseDtoList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostResponseDto> findRecentWatchPost(Integer pages) {
        List<PostResponseDto> postResponseDtoList = new ArrayList<>();
        Long userId = JwtUtil.getMemberId();
        Sort sort = Sort.by(Sort.Direction.DESC,"updatedAt");
        Pageable pageable = PageRequest.of(pages,5,sort);
        List<RecentWatchPost> recentWatchPostList = recentWatchRepository.findAllByUserId(userId,pageable);

        for (RecentWatchPost recentWatchPost : recentWatchPostList) {
            Long postId = recentWatchPost.getPostId();
            Post post = postRepository.findById(postId)
                    .orElseThrow(() -> new PostNotFoundException(postId));

            Room room = roomRepository.findById(post.getRoomId())
                    .orElseThrow(() -> new RoomNotFoundException(post.getRoomId()));
            List<Options> optionsList = optionsRepository.findByRoomId(room.getRoomId());

            Image roomImage = imageRepository.findMainImage(room.getRoomId(),0L)
                    .orElseThrow(() -> new ImageNotFoundException(room.getRoomId()));

            PostResponseDto postResponseDto = PostResponseDto.of(post,room,roomImage.getImageUrl(),optionsList);
            postResponseDtoList.add(postResponseDto);
        }

        return postResponseDtoList;
    }

    @Override
    @Transactional(readOnly = true)
    public PostDetailResponseDto findPostDetail(Long postId) {
        Long userId = JwtUtil.getMemberId();
        Boolean isWatched = false;
        StringBuilder imgUrls = new StringBuilder("");
        Long imageType;

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(postId));

        List<RecentWatchPost> recentWatchPostList = recentWatchRepository.findAllByUserId(userId);

        if (recentWatchPostList.size() >= 30) {
            recentWatchRepository.delete(recentWatchPostList.get(29));
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
        } else {
            recentWatchRepository.updateDate(LocalDateTime.now(),postId);
        }

        PostHeart postHeart = postHeartRepository.findByUserIdAndPostId(userId,post.getPostId());

        Room room = roomRepository.findById(post.getRoomId())
                .orElseThrow(
                        () -> new RoomNotFoundException(post.getRoomId())
                );

        List<Options> optionsList = optionsRepository.findByRoomId(room.getRoomId());

        for (imageType = 0L; imageType < 3L;imageType += 1) {
            List<Image> imgList = imageRepository.findByIdAndImageType(room.getRoomId(),imageType);

            for (Image img : imgList) {
                imgUrls.append(img.getImageUrl()).append(" ");
            }
        }

        return PostDetailResponseDto.of(post,room,postHeart,imgUrls.toString(),optionsList);
    }

    @Override
    @Transactional
    public PostCreateResponseDto addPost(PostRoomRequestDto postRoomRequestDto) {
        Long userId = JwtUtil.getMemberId();

        DateLog dateLog = DateLog.of(userId);

        dateLogRepository.save(dateLog);

        Room room = Room.of(postRoomRequestDto,userId,dateLog.getId());

        roomRepository.save(room);

        List<Options> optionsList = Options.of(room.getRoomId(),postRoomRequestDto);
        optionsRepository.saveAll(optionsList);

        Post post = Post.of(postRoomRequestDto,userId,room.getRoomId(),dateLog.getId());
        postRepository.save(post);

        return PostCreateResponseDto.of(post,room,optionsList);
    }

    @Override
    @Transactional
    public PostEditResponseDto editPost(PostRoomEditRequestDto postRoomEditRequestDto) {
        Long userId = JwtUtil.getMemberId();

        Post post = postRepository.findByPostIdAndUserId(postRoomEditRequestDto.getPostId(), userId)
                        .orElseThrow(() -> new PostNotFoundException(postRoomEditRequestDto.getPostId()));
        post.setTitle(postRoomEditRequestDto.getTitle());
        post.setContent(postRoomEditRequestDto.getContent());
        postRepository.save(post);

        return PostEditResponseDto.of(post);
    }

    @Override
    @Transactional
    public void deletePost(Long postId) {
        Long userId = JwtUtil.getMemberId();
        Post post = postRepository.findByPostIdAndUserId(postId,userId)
                        .orElseThrow(() -> new PostNotFoundException(postId));

        DateLog dateLog = DateLog.of(userId);
        dateLogRepository.save(dateLog);

        postRepository.delete(post);
    }

    @Override
    @Transactional
    public PostHeartResponseDto heartPost(PostHeartRequestDto postHeartRequestDto) {
        Long userId = JwtUtil.getMemberId();
        PostHeart postHeart = postHeartRepository.findByUserIdAndPostId(userId,postHeartRequestDto.getPostId());

        if (postHeart == null) {
            DateLog dateLog = DateLog.of(userId);
            dateLogRepository.save(dateLog);

            postHeart = PostHeart.builder()
                    .postId(postHeartRequestDto.getPostId())
                    .userId(userId)
                    .dateId(dateLog.getId())
                    .build();

            postHeartRepository.save(postHeart);
        } else {
            postHeartRepository.deleteById(postHeart.getPostHeartId());
        }

        return PostHeartResponseDto.of(postHeart.getPostId(),userId);
    }
}
