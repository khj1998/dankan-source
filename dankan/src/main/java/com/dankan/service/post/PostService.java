package com.dankan.service.post;

import com.dankan.dto.response.post.*;
import com.dankan.dto.request.post.PostHeartRequestDto;
import com.dankan.dto.request.post.PostRoomCreateRequestDto;
import com.dankan.dto.request.post.PostRoomEditRequestDto;
import java.util.List;
import java.util.UUID;

public interface PostService {
    PostResponseDto getPostByRoomId(UUID roomId);
    PostCreateResponseDto addPost(PostRoomCreateRequestDto postRoomCreateRequestDto);
    PostEditResponseDto editPost(PostRoomEditRequestDto postRoomEditRequestDto);
    List<PostResponseDto> findRecentPost(Integer pages);
    List<PostResponseDto> findHeartPost(Integer pages);
    List<PostResponseDto> findMyPost(Integer pages);
    List<PostResponseDto> findRecentWatchPost(Integer pages);

    PostDetailResponseDto findPostDetail(UUID postId);
    void deletePost(UUID postId);
    PostHeartResponseDto heartPost(PostHeartRequestDto postHeartRequestDto);
}
