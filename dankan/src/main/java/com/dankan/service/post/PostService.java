package com.dankan.service.post;

import com.dankan.dto.response.post.*;
import com.dankan.dto.request.post.PostHeartRequestDto;
import com.dankan.dto.request.post.PostRoomRequestDto;

import java.util.List;
import java.util.UUID;

public interface PostService {
    PostResponseDto getPostByRoomId(Long roomId);
    PostCreateResponseDto addPost(PostRoomRequestDto postRoomRequestDto);
    PostEditResponseDto editPost(PostRoomRequestDto postRoomRequestDto);
    List<PostResponseDto> findRecentPost(Integer pages);
    List<PostResponseDto> findHeartPost(Integer pages);
    List<PostResponseDto> findMyPost(Integer pages);
    PostDetailResponseDto findPostDetail(Long postId);
    void deletePost(Long postId);
    PostHeartResponseDto heartPost(PostHeartRequestDto postHeartRequestDto);
}
