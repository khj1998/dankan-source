package com.dankan.service.review;

import com.dankan.domain.Room;
import com.dankan.domain.RoomImage;
import com.dankan.domain.RoomReview;
import com.dankan.domain.User;
import com.dankan.domain.embedded.RoomReviewRate;
import com.dankan.dto.request.review.ReviewDetailRequestDto;
import com.dankan.dto.response.review.ReviewDetailResponseDto;
import com.dankan.dto.response.review.ReviewImageResponseDto;
import com.dankan.dto.response.review.ReviewRateResponseDto;
import com.dankan.dto.response.review.ReviewResponseDto;
import com.dankan.dto.request.review.ReviewRequestDto;
import com.dankan.exception.review.ReviewNotFoundException;
import com.dankan.exception.room.RoomImageNotFoundException;
import com.dankan.exception.room.RoomNotFoundException;
import com.dankan.exception.user.UserIdNotFoundException;
import com.dankan.repository.ReviewRepository;
import com.dankan.repository.RoomImageRepository;
import com.dankan.repository.RoomRepository;
import com.dankan.repository.UserRepository;
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
public class ReviewServiceImpl implements ReviewService {
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;
    private final RoomRepository roomRepository;
    private final RoomImageRepository roomImageRepository;

    private final Integer PAGING_COUNT = 5;

    public ReviewServiceImpl(UserRepository userRepository
            ,ReviewRepository reviewRepository
            ,RoomRepository roomRepository
            ,RoomImageRepository roomImageRepository) {
        this.userRepository = userRepository;
        this.reviewRepository = reviewRepository;
        this.roomRepository = roomRepository;
        this.roomImageRepository = roomImageRepository;
    }

    @Override
    @Transactional
    public ReviewResponseDto addReview(ReviewRequestDto reviewRequestDto) {
        UUID userId = JwtUtil.getMemberId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserIdNotFoundException(userId.toString()));

        Room room = roomRepository.findFirstByRoomAddress_Address(reviewRequestDto.getAddress())
                .orElseThrow(() -> new RoomNotFoundException(reviewRequestDto.getAddress()));

        RoomReview roomReview = RoomReview.of(reviewRequestDto,user,room.getRoomId());
        reviewRepository.save(roomReview);

        return ReviewResponseDto.of(user,roomReview,room,null);
    }

    @Override
    @Transactional
    public ReviewImageResponseDto addReviewImage(UUID reviewId,String imgUrl) {
        RoomReview roomReview = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewNotFoundException(reviewId));
        roomReview.setImageUrl(imgUrl);
        reviewRepository.save(roomReview);

        return ReviewImageResponseDto.builder()
                .imgUrl(imgUrl)
                .build();
    }

    @Override
    @Transactional
    public void deleteReview(UUID reviewId) {
        UUID userId = JwtUtil.getMemberId();
        RoomReview roomReview = reviewRepository.findByUserIdAndReviewId(userId,reviewId)
                .orElseThrow(() -> new ReviewNotFoundException(reviewId));
        reviewRepository.delete(roomReview);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReviewResponseDto> findRecentReview(Integer pages) {
        List<ReviewResponseDto> responseDtoList = new ArrayList<>();
        Sort sort = Sort.by(Sort.Direction.DESC,"updatedAt");
        Pageable pageable =  PageRequest.of(pages,PAGING_COUNT,sort);
        Slice<RoomReview> roomReviewList = reviewRepository.findAll(pageable);

        for (RoomReview roomReview : roomReviewList) {
            Room room  = roomRepository.findById(roomReview.getRoomId())
                  .orElseThrow(() -> new RoomNotFoundException(roomReview.getRoomId().toString()));
            User user = userRepository.findById(room.getUserId())
                    .orElseThrow(() -> new UserIdNotFoundException(room.getUserId().toString()));
            ReviewResponseDto responseDto = ReviewResponseDto.of(user,roomReview,room,roomReview.getImageUrl());
            responseDtoList.add(responseDto);
        }

        return responseDtoList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReviewResponseDto> findReviewByStar(Integer pages) {
        List<ReviewResponseDto> responseDtoList = new ArrayList<>();
        Sort sort = Sort.by(Sort.Direction.DESC,"roomReviewRate.totalRate");
        Pageable pageable = PageRequest.of(pages,PAGING_COUNT,sort);
        Slice<RoomReview> roomReviewList = reviewRepository.findAll(pageable);

        for (RoomReview roomReview : roomReviewList) {
            Room room = roomRepository.findById(roomReview.getRoomId())
                    .orElseThrow(() -> new RoomNotFoundException(roomReview.getRoomId()));
            ReviewResponseDto responseDto = ReviewResponseDto.of(room,roomReview,roomReview.getImageUrl());
            responseDtoList.add(responseDto);
        }

        return responseDtoList;
    }

    @Override
    @Transactional(readOnly = true)
    public ReviewRateResponseDto findReviewRate(String address) {
        Long reviewCount;
        RoomReviewRate roomReviewRate = RoomReviewRate.init();
        Room room = roomRepository.findFirstByRoomAddress_Address(address)
                .orElseThrow(() -> new RoomNotFoundException(address));

        // 하나의 도로명 주소에는 여러 방이 있을 수 있습니다. 어떤 이미지를 대표로 가져올지 고려해봐야 합니다.
        RoomImage roomImage = roomImageRepository.findByRoomIdAndImageType(room.getRoomId(),0L)
                .orElseThrow(() -> new RoomImageNotFoundException(room.getRoomId()));

        List<RoomReview> reviewList = reviewRepository.findByAddress(address);
        for (RoomReview roomReview : reviewList) {
            roomReviewRate.plusRate(roomReview);
        }

        reviewCount = (long) reviewList.size();

        return ReviewRateResponseDto.of(roomReviewRate,room, reviewCount,roomImage.getRoomImageUrl());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReviewDetailResponseDto> findReviewDetail(ReviewDetailRequestDto reviewDetailRequestDto) {
        List<ReviewDetailResponseDto> responseDtoList = new ArrayList<>();
        Sort sort = Sort.by(Sort.Direction.DESC,"updatedAt");
        Pageable pageable = PageRequest.of(reviewDetailRequestDto.getPages(),5,sort);
        List<RoomReview> roomReviewList = reviewRepository.findByAddress(reviewDetailRequestDto.getAddress(),pageable);

        for (RoomReview roomReview : roomReviewList) {
            UUID userId = roomReview.getUserId();
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new UserIdNotFoundException(userId.toString()));
            ReviewDetailResponseDto reviewDetailResponseDto = ReviewDetailResponseDto.of(user,roomReview);
            responseDtoList.add(reviewDetailResponseDto);
        }

        return responseDtoList;
    }
}
