package com.dankan.service.review;

import com.dankan.domain.Room;
import com.dankan.domain.RoomReview;
import com.dankan.domain.User;
import com.dankan.domain.embedded.RoomReviewRate;
import com.dankan.dto.response.review.ReviewDetailResponseDto;
import com.dankan.dto.response.review.ReviewRateResponseDto;
import com.dankan.dto.response.review.ReviewResponseDto;
import com.dankan.dto.request.review.ReviewDetailRequestDto;
import com.dankan.dto.request.review.ReviewRequestDto;
import com.dankan.exception.review.ReviewNotFoundException;
import com.dankan.exception.room.RoomNotFoundException;
import com.dankan.exception.user.UserIdNotFoundException;
import com.dankan.repository.ReviewRepository;
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

    public ReviewServiceImpl(UserRepository userRepository
            ,ReviewRepository reviewRepository
            ,RoomRepository roomRepository) {
        this.userRepository = userRepository;
        this.reviewRepository = reviewRepository;
        this.roomRepository = roomRepository;
    }

    @Override
    @Transactional
    public ReviewResponseDto addReview(ReviewRequestDto reviewRequestDto) {
        Long userId = JwtUtil.getMemberId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserIdNotFoundException(userId.toString()));

        Room room = roomRepository.findFirstByRoomAddress_Address(reviewRequestDto.getAddress())
                .orElseThrow(() -> new RoomNotFoundException(reviewRequestDto.getAddress()));

        RoomReview roomReview = RoomReview.of(reviewRequestDto,user,room.getRoomId());
        reviewRepository.save(roomReview);

        return ReviewResponseDto.of(user,roomReview,room);
    }

    @Override
    @Transactional
    public void deleteReview(Long reviewId) {
        Long userId = JwtUtil.getMemberId();
        RoomReview roomReview = reviewRepository.findByUserIdAndReviewId(userId,reviewId)
                .orElseThrow(() -> new ReviewNotFoundException(reviewId));
        reviewRepository.delete(roomReview);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReviewResponseDto> findRecentReview(Integer pages) {
        List<ReviewResponseDto> responseDtoList = new ArrayList<>();
        Sort sort = Sort.by(Sort.Direction.DESC,"updatedAt");
        Pageable pageable =  PageRequest.of(pages,5,sort);
        Slice<RoomReview> roomReviewList = reviewRepository.findAll(pageable);

        for (RoomReview roomReview : roomReviewList) {
            Room room  = roomRepository.findById(roomReview.getRoomId())
                  .orElseThrow(() -> new RoomNotFoundException(roomReview.getRoomId().toString()));
            User user = userRepository.findById(room.getUserId())
                    .orElseThrow(() -> new UserIdNotFoundException(room.getUserId().toString()));
            ReviewResponseDto responseDto = ReviewResponseDto.of(user,roomReview,room);
            responseDtoList.add(responseDto);
        }

        return responseDtoList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReviewResponseDto> findReviewByStar(Integer pages) {
        List<ReviewResponseDto> responseDtoList = new ArrayList<>();
        Sort sort = Sort.by(Sort.Direction.DESC,"roomReviewRate.totalRate");
        Pageable pageable = PageRequest.of(pages,5,sort);
        Slice<RoomReview> roomReviewList = reviewRepository.findAll(pageable);

        for (RoomReview roomReview : roomReviewList) {
            Room room = roomRepository.findById(roomReview.getRoomId())
                    .orElseThrow(() -> new RoomNotFoundException(roomReview.getRoomId()));
            ReviewResponseDto responseDto = ReviewResponseDto.of(room,roomReview);
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

        List<RoomReview> reviewList = reviewRepository.findByAddress(address);
        for (RoomReview roomReview : reviewList) {
            roomReviewRate.plusRate(roomReview);
        }

        reviewCount = (long) reviewList.size();

        return ReviewRateResponseDto.of(roomReviewRate,room, reviewCount);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReviewDetailResponseDto> findReviewDetail(ReviewDetailRequestDto reviewDetailRequestDto) {
        List<ReviewDetailResponseDto> responseDtoList = new ArrayList<>();
        Sort sort = Sort.by(Sort.Direction.DESC,"updatedAt");
        Pageable pageable = PageRequest.of(reviewDetailRequestDto.getPages(),5,sort);
        List<RoomReview> roomReviewList = reviewRepository.findByAddress(reviewDetailRequestDto.getAddress(),pageable);

        for (RoomReview roomReview : roomReviewList) {
            Long userId = roomReview.getUserId();
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new UserIdNotFoundException(userId.toString()));
            ReviewDetailResponseDto reviewDetailResponseDto = ReviewDetailResponseDto.of(user,roomReview);
            responseDtoList.add(reviewDetailResponseDto);
        }

        return responseDtoList;
    }
}
