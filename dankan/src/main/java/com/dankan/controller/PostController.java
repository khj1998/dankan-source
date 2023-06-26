package com.dankan.controller;

import com.dankan.dto.response.post.*;
import com.dankan.dto.response.room.RoomImageResponseDto;
import com.dankan.dto.request.post.PostHeartRequestDto;
import com.dankan.dto.request.post.PostRoomRequestDto;
import com.dankan.service.post.PostService;
import com.dankan.service.room.RoomService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Slf4j
@CrossOrigin
@Api(tags = {"매매 게시물 API"})
@RequestMapping("/post")
@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final RoomService roomService;

    @ApiOperation("매물 번호로 조회 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "매물 번호로 조회 성공 "),
            @ApiResponse(responseCode = "401",description = "인증되지 않은 사용자"),
            @ApiResponse(responseCode = "403",description = "유저가 Member | Admin 권한이 없음"),
            @ApiResponse(responseCode = "404",description = "매물 번호 조회에 실패함")
    })
    @GetMapping
    public ResponseEntity<PostResponseDto> getPostByRoomId(@RequestParam("roomId") UUID roomId) {
        PostResponseDto responseDto = postService.getPostByRoomId(roomId);
        return ResponseEntity.ok(responseDto);
    }

    @ApiOperation("찜한 매물 조회 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "찜한 매물 조회 성공 "),
            @ApiResponse(responseCode = "401",description = "인증되지 않은 사용자"),
            @ApiResponse(responseCode = "403",description = "유저가 Member | Admin 권한이 없음"),
            @ApiResponse(responseCode = "404",description = "찜한 매물 조회에 실패함")
    })
    @GetMapping("/heart")
    public ResponseEntity<List<PostResponseDto>> getHeartPost(@RequestParam("pages") Integer pages) {
        List<PostResponseDto> responseDtoList = postService.findHeartPost(pages);
        return ResponseEntity.ok(responseDtoList);
    }

    @ApiOperation("매매 게시물 최신순 조회 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "매매 게시물 최신순 조회 성공 "),
            @ApiResponse(responseCode = "401",description = "인증되지 않은 사용자"),
            @ApiResponse(responseCode = "403",description = "유저가 Member | Admin 권한이 없음"),
            @ApiResponse(responseCode = "404",description = "매매 게시물 최신순 조회에 실패함")
    })
    @GetMapping("/recent")
    public ResponseEntity<List<PostResponseDto>> getRecentPost(@RequestParam("pages") Integer pages) {
        List<PostResponseDto> responseDtoList = postService.findRecentPost(pages);
        return ResponseEntity.ok(responseDtoList);
    }

    @ApiOperation("등록한 매매 게시물 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "등록한 매매 게시물 조회 성공 "),
            @ApiResponse(responseCode = "401",description = "인증되지 않은 사용자"),
            @ApiResponse(responseCode = "403",description = "유저가 Member | Admin 권한이 없음"),
            @ApiResponse(responseCode = "404",description = "등록한 매매 게시물 조회에 실패함")
    })
    @GetMapping("/my-room")
    public ResponseEntity<List<PostResponseDto>> getMyPost(@RequestParam("pages") Integer pages) {
        List<PostResponseDto> responseDtoList = postService.findMyPost(pages);
        return ResponseEntity.ok(responseDtoList);
    }

    @ApiOperation("매매 게시물 상세 조회 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "매매 게시물 상세 조회 성공 "),
            @ApiResponse(responseCode = "401",description = "인증되지 않은 사용자"),
            @ApiResponse(responseCode = "403",description = "유저가 Member | Admin 권한이 없음"),
            @ApiResponse(responseCode = "404",description = "매매 게시물 상세 조회에 실패함")
    })
    @GetMapping("/detail")
    public ResponseEntity<PostDetailResponseDto> getPostDetail(@RequestParam("postId") UUID postId) {
        PostDetailResponseDto responseDto = postService.findPostDetail(postId);
        return ResponseEntity.ok(responseDto);
    }

    @ApiOperation("매매 게시물 등록 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "매매 게시물 등록 성공 "),
            @ApiResponse(responseCode = "401",description = "인증되지 않은 사용자"),
            @ApiResponse(responseCode = "403",description = "유저가 Member | Admin 권한이 없음"),
            @ApiResponse(responseCode = "404",description = "매매 게시물 등록에 실패함")
    })
    @PostMapping
    public ResponseEntity<PostCreateResponseDto> addPost(@RequestBody PostRoomRequestDto postRoomRequestDto) {
        PostCreateResponseDto responseDto = postService.addPost(postRoomRequestDto);
        return ResponseEntity.ok(responseDto);
    }

    @ApiOperation("매물 이미지 등록 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "매물 이미지 등록 성공"),
            @ApiResponse(responseCode = "401",description = "인증되지 않은 사용자"),
            @ApiResponse(responseCode = "403",description = "유저가 Member | Admin 권한이 없음"),
            @ApiResponse(responseCode = "404",description = "매물 이미지 등록 실패")
    })
    @PostMapping("/image")
    public ResponseEntity<RoomImageResponseDto> addPostImage(
            @RequestParam("type") String type,
            @RequestParam(value = "image") List<MultipartFile> roomImages) {
        RoomImageResponseDto responseDto = roomService.addRoomImage(roomImages,type);
        return ResponseEntity.ok(null);
    }
    
    @ApiOperation("매매 게시물 수정 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "매매 게시물 수정 성공"),
            @ApiResponse(responseCode = "401",description = "인증되지 않은 사용자"),
            @ApiResponse(responseCode = "403",description = "사용자가 Member | Admin 권한이 없음"),
            @ApiResponse(responseCode = "404",description = "매매 게시물 수정에 실패함")
    })
    @PostMapping("/edit")
    public ResponseEntity<PostEditResponseDto> editPost(@RequestBody PostRoomRequestDto postRoomRequestDto) {
        PostEditResponseDto responseDto = postService.editPost(postRoomRequestDto);
        return ResponseEntity.ok(responseDto);
    }

    @ApiOperation("매매 게시물 찜 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "매매 게시물 찜 추가/취소 성공"),
            @ApiResponse(responseCode = "401",description = "인증되지 않은 사용자"),
            @ApiResponse(responseCode = "403",description = "사용자가 Member | Admin 권한이 없음"),
            @ApiResponse(responseCode = "404",description = "매매 게시물 찜에 실패함")
    })
    @PostMapping("/heart")
    public ResponseEntity<PostHeartResponseDto> heartPost(@RequestBody PostHeartRequestDto postHeartRequestDto) {
        PostHeartResponseDto responseDto = postService.heartPost(postHeartRequestDto);
        return ResponseEntity.ok(responseDto);
    }

    @ApiOperation("매매 게시물 삭제 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "매매 게시물 삭제 성공"),
            @ApiResponse(responseCode = "401",description = "인증되지 않은 사용자"),
            @ApiResponse(responseCode = "403",description = "사용자가 Member | Admin 권한이 없음"),
            @ApiResponse(responseCode = "404",description = "매매 게시물 삭제에 실패함")
    })
    @DeleteMapping("/delete")
    public ResponseEntity deletePost(@RequestParam UUID postId) {
        postService.deletePost(postId);
        return ResponseEntity.ok().build();
    }
}
