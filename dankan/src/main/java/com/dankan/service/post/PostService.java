package com.dankan.service.post;

import com.dankan.dto.response.post.*;
import com.dankan.dto.resquest.post.PostHeartRequestDto;
import com.dankan.dto.resquest.post.PostRoomRequestDto;

import java.util.List;
import java.util.UUID;

public interface PostService {
    PostResponseDto getPostByRoomId(UUID roomId);
    PostCreateResponseDto addPost(PostRoomRequestDto postRoomRequestDto);
    PostEditResponseDto editPost(PostRoomRequestDto postRoomRequestDto);
    List<PostResponseDto> findRecentPost(Integer pages);
    List<PostResponseDto> findHeartPost(Integer pages);
    List<PostResponseDto> findMyPost(Integer pages);
    PostDetailResponseDto findPostDetail(UUID postId);
    void deletePost(UUID postId);
    PostHeartResponseDto heartPost(PostHeartRequestDto postHeartRequestDto);
}
