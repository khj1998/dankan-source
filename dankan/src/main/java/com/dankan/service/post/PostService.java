package com.dankan.service.post;

import com.dankan.dto.request.post.PostFilterRequestDto;
import com.dankan.dto.request.post.PostRoomEditRequestDto;
import com.dankan.dto.response.post.*;
import com.dankan.dto.request.post.PostHeartRequestDto;
import com.dankan.dto.request.post.PostRoomRequestDto;
import java.util.List;

public interface PostService {
    List<PostFilterResponseDto> getPostByFilter(PostFilterRequestDto postFilterRequestDto);
    PostResponseDto getPostByRoomId(Long roomId);
    PostCreateResponseDto addPost(PostRoomRequestDto postRoomRequestDto);
    PostEditResponseDto editPost(PostRoomEditRequestDto postRoomEditRequestDto);
    List<PostResponseDto> findRecentPost(Integer pages);
    List<PostResponseDto> findHeartPost(Integer pages);
    List<PostResponseDto> findMyPost(Integer pages);
    PostDetailResponseDto findPostDetail(Long postId);
    List<PostResponseDto> findRecentWatchPost(Integer pages);
    void deletePost(Long postId);
    PostHeartResponseDto heartPost(PostHeartRequestDto postHeartRequestDto);
    Boolean setTradeEnd(Long postId);
    List<PostResponseDto> getTradeEndPost(Integer  pages);
}
