package com.dankan.controller;

import com.dankan.dto.request.image.ImageEditRequestDto;
import com.dankan.dto.request.post.*;
import com.dankan.dto.request.image.ImageRequestDto;
import com.dankan.dto.response.post.*;
import com.dankan.dto.response.image.ImageResponseDto;
import com.dankan.dto.request.post.PostHeartRequestDto;
import com.dankan.repository.PostRepository;
import com.dankan.service.post.PostService;
import com.dankan.service.image.ImageService;
import com.dankan.service.s3.S3UploadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@CrossOrigin
@Api(tags = {"매매 게시물 API"})
@RequestMapping("/post")
@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final ImageService imageService;
    private final S3UploadService s3UploadService;

    @ApiOperation("매물 번호로 조회 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "매물 번호로 조회 성공 ")
    })
    @GetMapping
    public ResponseEntity<PostResponseDto> getPostByRoomId(@RequestParam("roomId") Long roomId) {
        PostResponseDto responseDto = postService.getPostByRoomId(roomId);
        return ResponseEntity.ok(responseDto);
    }

    @ApiOperation("찜한 매물 조회 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "찜한 매물 조회 성공 ")
    })
    @GetMapping("/heart")
    public ResponseEntity<List<PostResponseDto>> getHeartPost(@RequestParam("pages") Integer pages) {
        List<PostResponseDto> responseDtoList = postService.findHeartPost(pages);
        return ResponseEntity.ok(responseDtoList);
    }

    @ApiOperation("최근 본 매물 N개 조회 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "최근 본 매물 조회 성공")
    })
    @GetMapping("/recent/watch")
    public ResponseEntity<List<PostResponseDto>> getRecentWatchPost(@RequestParam("pages") Integer pages) {
        List<PostResponseDto> responseDtoList = postService.findRecentWatchPost(pages);
        return ResponseEntity.ok(responseDtoList);
    }

    @ApiOperation("매매 게시물 최신순 조회 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "매매 게시물 최신순 조회 성공 ")
    })
    @GetMapping("/recent")
    public ResponseEntity<List<PostResponseDto>> getRecentPost(@RequestParam("pages") Integer pages) {
        List<PostResponseDto> responseDtoList = postService.findRecentPost(pages);
        return ResponseEntity.ok(responseDtoList);
    }

    @ApiOperation("등록한 매매 게시물 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "등록한 매매 게시물 조회 성공 ")
    })
    @GetMapping("/my-room")
    public ResponseEntity<List<PostResponseDto>> getMyPost(@RequestParam("pages") Integer pages) {
        List<PostResponseDto> responseDtoList = postService.findMyPost(pages);
        return ResponseEntity.ok(responseDtoList);
    }

    @ApiOperation("매매 게시물 상세 조회 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "매매 게시물 상세 조회 성공 ")
    })
    @GetMapping("/detail")
    public ResponseEntity<PostDetailResponseDto> getPostDetail(@RequestParam("postId") Long postId) {
        PostDetailResponseDto responseDto = postService.findPostDetail(postId);
        return ResponseEntity.ok(responseDto);
    }

    @ApiOperation("매매 게시물 필터 조회 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "매매 게시물 필터 조회 성공"),
            @ApiResponse(responseCode = "401",description = "인증되지 않은 사용자"),
            @ApiResponse(responseCode = "403",description = "유저가 Member | Admin 권한이 없음"),
            @ApiResponse(responseCode = "404",description = "매매 게시물 필터 조회 실패")
    })
    @PostMapping("/filter")
    public ResponseEntity<List<PostFilterResponseDto>> doPostFilter(@RequestBody PostFilterRequestDto postFilterRequestDto) {
        List<PostFilterResponseDto> responseDtoList = postService.getPostByFilter(postFilterRequestDto);
        return ResponseEntity.ok(responseDtoList);
    }

    @ApiOperation("매매 게시물 등록 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "매매 게시물 등록 성공 ")
    })
    @PostMapping
    public ResponseEntity<PostCreateResponseDto> addPost(@RequestBody PostRoomRequestDto postRoomRequestDto) {
        PostCreateResponseDto responseDto = postService.addPost(postRoomRequestDto);
        return ResponseEntity.ok(responseDto);
    }

    @ApiOperation("매물 이미지 등록 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "매물 이미지 등록 성공")
    })
    @PostMapping(value = "/image",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ImageResponseDto> addPostImage(@ModelAttribute ImageRequestDto imageRequestDto) throws IOException {
        String imgUrl = "";

        for (MultipartFile multipartFile : imageRequestDto.getMultipartFileList()) {
            imgUrl += s3UploadService.upload(multipartFile, "room-image") + " ";
        }

        ImageResponseDto responseDto = imageService.addRoomImages(imageRequestDto,imgUrl);
        return ResponseEntity.ok(responseDto);
    }
    
    @ApiOperation("매매 게시물 수정 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "매매 게시물 수정 성공")
    })
    @PostMapping("/edit")
    public ResponseEntity<PostEditResponseDto> editPost(@RequestBody PostRoomEditRequestDto postRoomEditRequestDto) {
        PostEditResponseDto responseDto = postService.editPost(postRoomEditRequestDto);
        return ResponseEntity.ok(responseDto);
    }

    @ApiOperation("매물 이미지 수정 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "매물 이미지 수정 성공")
    })
    @PostMapping(value = "/image/edit",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ImageResponseDto> editRoomImage(@ModelAttribute ImageEditRequestDto imageEditRequestDto) throws IOException {
        String imgUrl = "";

        for (MultipartFile multipartFile : imageEditRequestDto.getMultipartFileList()) {
            imgUrl += s3UploadService.upload(multipartFile, "room-image") + " ";
        }

        ImageResponseDto responseDto = imageService.editRoomImages(imageEditRequestDto,imgUrl);
        return ResponseEntity.ok(responseDto);
    }

    @ApiOperation("매매 게시물 찜 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "매매 게시물 찜 추가/취소 성공")
    })
    @PostMapping("/heart")
    public ResponseEntity<PostHeartResponseDto> heartPost(@RequestBody PostHeartRequestDto postHeartRequestDto) {
        PostHeartResponseDto responseDto = postService.heartPost(postHeartRequestDto);
        return ResponseEntity.ok(responseDto);
    }

    @ApiOperation("매매 게시물 삭제 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "매매 게시물 삭제 성공")
    })
    @DeleteMapping("/delete")
    public ResponseEntity deletePost(@RequestParam Long postId) {
        postService.deletePost(postId);
        return ResponseEntity.ok().build();
    }

    @ApiOperation("게시물 거래완료 처리 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "게시물 거래완료 처리 성공")
    })
    @PostMapping("/trade-end/add")
    public ResponseEntity<Boolean> addTradeEnd(@RequestParam Long postId) {
        return ResponseEntity.ok(postService.setTradeEnd(postId));
    }

    @ApiOperation("내 거래완료 목록 확인 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "거래완료 게시물 조회 성공")
    })
    @GetMapping("/trade-end/list")
    public ResponseEntity<List<PostResponseDto>> getTradeEndPost(@RequestParam Integer pages) {
        List<PostResponseDto> responseDtoList = postService.getTradeEndPost(pages);
        return ResponseEntity.ok(responseDtoList);
    }

    @ApiOperation("주소로 최근 매물 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "주소로 최근 매물 조회 성공")
    })
    @GetMapping("/recent/address")
    public ResponseEntity<List<PostResponseDto>> getRecentPostByAddress(@RequestParam Integer pages,
                                                                        @RequestParam String address) {
        List<PostResponseDto> responseDtoList = postService.findRecentPostByAddress(pages,address);
        return ResponseEntity.ok(responseDtoList);
    }
}
