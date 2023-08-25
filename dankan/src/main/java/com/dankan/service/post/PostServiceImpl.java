package com.dankan.service.post;

import com.dankan.domain.*;
import com.dankan.dto.request.post.PostFilterRequestDto;
import com.dankan.dto.request.post.PostRoomEditRequestDto;
import com.dankan.dto.response.post.*;
import com.dankan.dto.request.post.PostHeartRequestDto;
import com.dankan.dto.request.post.PostRoomRequestDto;
import com.dankan.enum_converter.*;
import com.dankan.exception.image.ImageNotFoundException;
import com.dankan.exception.post.PostNotFoundException;
import com.dankan.exception.room.RoomNotFoundException;
import com.dankan.exception.user.UserIdNotFoundException;
import com.dankan.repository.*;
import com.dankan.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
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
    private final UserRepository userRepository;

    public PostServiceImpl(
            PostRepository postRepository,
            RoomRepository roomRepository,
            PostHeartRepository postHeartRepository,
            DateLogRepository dateLogRepository,
            RecentWatchRepository recentWatchRepository,
            OptionsRepository optionsRepository,
            ImageRepository imageRepository,
            UserRepository userRepository) {
        this.postRepository = postRepository;
        this.roomRepository = roomRepository;
        this.postHeartRepository = postHeartRepository;
        this.recentWatchRepository = recentWatchRepository;
        this.dateLogRepository = dateLogRepository;
        this.optionsRepository = optionsRepository;
        this.imageRepository = imageRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostFilterResponseDto> getPostByFilter(PostFilterRequestDto postFilterRequestDto) {
        Long memberId = JwtUtil.getMemberId();
        List<PostFilterResponseDto> responseDtoList = new ArrayList<>();
        List<Room> roomList = roomRepository.findRoomByFilter(postFilterRequestDto);

        if (roomList != null) {

            for (Room room : roomList) {

                if (!room.getIsTradeable()) {
                    continue;
                }

                Post post = postRepository.findByRoomId(room.getRoomId(),true)
                        .orElseThrow(() -> new PostNotFoundException(room.getRoomId()));

                List<Options> optionsList = optionsRepository.findByRoomId(room.getRoomId());

                if (!isOptionSame(optionsList,postFilterRequestDto)) {
                    continue;
                }

                PostHeart postHeart = postHeartRepository.findByUserIdAndPostId(memberId,post.getPostId());

                Image roomImage = imageRepository.findMainImage(room.getRoomId(),0L)
                        .orElseThrow(() -> new ImageNotFoundException(room.getRoomId()));

                PostFilterResponseDto responseDto = PostFilterResponseDto.of(post,room,postHeart, roomImage.getImageUrl(),optionsList);
                responseDtoList.add(responseDto);
            }
        }

        if (postFilterRequestDto.getLowCostOrder() == null && postFilterRequestDto.getHeartOrder() == null) {
            responseDtoList.sort(
                    Comparator.comparing(PostFilterResponseDto::getCreatedAt).reversed()
            );
        } else if (postFilterRequestDto.getLowCostOrder() != null) {
            Comparator<PostFilterResponseDto> createdAtSort = Comparator.comparing(PostFilterResponseDto::getCreatedAt).reversed();
            responseDtoList.sort(
                    Comparator.comparing(PostFilterResponseDto::getPrice)
                            .thenComparing(createdAtSort)
            );
        } else if (postFilterRequestDto.getHeartOrder() != null) {
            responseDtoList.sort(
                    Comparator.comparing(PostFilterResponseDto::getIsHearted)
                            .thenComparing(PostFilterResponseDto::getCreatedAt).reversed()
            );
        }

        return responseDtoList;
    }

    private Boolean isOptionSame(List<Options> optionsList,PostFilterRequestDto postFilterRequestDto) {
        Boolean isSame = true;
        String etcOptionValues = "0123";

        for (Options options : optionsList) {
            if (postFilterRequestDto.getDealType() != null && options.getCodeKey().equals("DealType")) {
                String dealTypeValue = DealTypeEnum.getDealTypeValue(postFilterRequestDto.getDealType());

                if (!dealTypeValue.equals(options.getValue())) {
                    isSame = false;
                    break;
                }
            }

            if (postFilterRequestDto.getPriceType() != null && options.getCodeKey().equals("PriceType")) {
                String priceTypeValue = PriceTypeEnum.getPriceTypeValue(postFilterRequestDto.getPriceType());

                if (!priceTypeValue.equals(options.getValue())) {
                    isSame = false;
                    break;
                }
            }

            if (postFilterRequestDto.getRoomType().size() > 0 && options.getCodeKey().equals("RoomType")) {
                String roomTypeValue = "";

                for (String roomType : postFilterRequestDto.getRoomType()) {
                    roomTypeValue += RoomTypeEnum.getRoomTypeValue(roomType);
                }

                if (!roomTypeValue.contains(options.getValue())) {
                    isSame = false;
                    break;
                }
            }

            if (postFilterRequestDto.getRoomStructure().size() > 0 && options.getCodeKey().equals("StructureType")) {
                String roomStructureValue = "";

                for (String roomStructure : postFilterRequestDto.getRoomStructure()) {
                    roomStructureValue += StructureTypeEnum.getStructureTypeValue(roomStructure);
                }

                if (!roomStructureValue.contains(options.getValue())) {
                    isSame = false;
                    break;
                }
            }

            if (postFilterRequestDto.getFullOption() != null && options.getCodeKey().equals("Option")) {
                if (!options.getValue().contains("0123")) {
                    isSame = false;
                    break;
                }
            }

            if (postFilterRequestDto.getCanPark() != null && options.getCodeKey().equals("EtcOption")) {
                String parkValue = EtcOptionTypeEnum.getEtcOptionTypeValue(postFilterRequestDto.getCanPark());

                if (!parkValue.equals(options.getValue())) {
                    isSame = false;
                    break;
                }
            }

            if (postFilterRequestDto.getPet() != null && options.getCodeKey().equals("EtcOption")) {
                String petValue = EtcOptionTypeEnum.getEtcOptionTypeValue(postFilterRequestDto.getPet());

                if (!etcOptionValues.contains(petValue)) {
                    isSame = false;
                    break;
                }
            }

            if (postFilterRequestDto.getOnlyWomen() != null && options.getCodeKey().equals("EtcOption")) {
                String onlyWomenValue = EtcOptionTypeEnum.getEtcOptionTypeValue(postFilterRequestDto.getOnlyWomen());

                if (!etcOptionValues.contains(onlyWomenValue)) {
                    isSame = false;
                    break;
                }
            }

            if (postFilterRequestDto.getLoan() != null && options.getCodeKey().equals("EtcOption")) {
                String loanValue =  EtcOptionTypeEnum.getEtcOptionTypeValue(postFilterRequestDto.getLoan());

                if (!etcOptionValues.contains(loanValue)) {
                    isSame = false;
                    break;
                }
            }
        }

        return isSame;
    }

    @Override
    @Transactional(readOnly = true)
    public PostResponseDto getPostByRoomId(Long roomId) {
        Long userId = JwtUtil.getMemberId();
        Room room = roomRepository.findById(roomId,true)
                .orElseThrow(() -> new RoomNotFoundException(roomId));

        Post post = postRepository.findByRoomId(roomId,true)
                .orElseThrow(() -> new PostNotFoundException(roomId));

        PostHeart postHeart = postHeartRepository.findByUserIdAndPostId(userId,post.getPostId());
        List<Options> optionsList = optionsRepository.findByRoomId(room.getRoomId());

        Image roomImage = imageRepository.findMainImage(room.getRoomId(),0L)
                .orElseThrow(() -> new ImageNotFoundException(room.getRoomId()));

        return PostResponseDto.of(post,room,postHeart,roomImage.getImageUrl(),optionsList);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostResponseDto> findRecentPostByAddress(Integer pages,String address) {
        Long userId = JwtUtil.getMemberId();
        List<PostResponseDto> responseDtoList = new ArrayList<>();
        Sort sort = Sort.by(Sort.Direction.DESC,"created_at");
        Pageable pageable = PageRequest.of(pages,5,sort);

        Slice<Room> roomList = roomRepository.findRoomByAddress(address,true,pageable);

        for (Room room : roomList) {
            Post post = postRepository.findByRoomId(room.getRoomId(),true)
                    .orElseThrow(() -> new PostNotFoundException(room.getRoomId()));

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
    @Transactional
    public List<PostResponseDto> findHeartPost(Integer pages) {
        List<PostResponseDto> postResponseDtoList = new ArrayList<>();
        Long userId = JwtUtil.getMemberId();

        Sort sort = Sort.by(Sort.Direction.DESC,"createdAt");
        Pageable pageable = PageRequest.of(pages,5,sort);

        List<PostHeart> postHeartList = postHeartRepository.findByUserId(userId,pageable);

        for (PostHeart postHeart : postHeartList) {
            Post post = postRepository.findById(postHeart.getPostId())
                    .orElseThrow(() -> new PostNotFoundException(postHeart.getPostId()));

            if (!post.getIsShown()) {
                postHeartRepository.delete(postHeart);
                continue;
            }

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

        Sort sort = Sort.by(Sort.Direction.DESC,"createdAt");
        Pageable pageable = PageRequest.of(pages,5,sort);

        List<Post> postList = postRepository.findByUserId(userId,true,pageable);

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
    @Transactional
    public List<PostResponseDto> findRecentWatchPost(Integer pages) {
        List<PostResponseDto> postResponseDtoList = new ArrayList<>();
        Long userId = JwtUtil.getMemberId();
        Sort sort = Sort.by(Sort.Direction.DESC,"createdAt");
        Pageable pageable = PageRequest.of(pages,5,sort);
        List<RecentWatchPost> recentWatchPostList = recentWatchRepository.findAllByUserId(userId,pageable);

        for (RecentWatchPost recentWatchPost : recentWatchPostList) {
            Long postId = recentWatchPost.getPostId();
            Post post = postRepository.findById(postId)
                    .orElseThrow(() -> new PostNotFoundException(postId));

            if (!post.getIsShown()) {
                recentWatchRepository.delete(recentWatchPost);
                continue;
            }

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
    @Transactional
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

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserIdNotFoundException(userId.toString()));

        Room room = Room.of(postRoomRequestDto,user);

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

    @Override
    @Transactional
    public Boolean setTradeEnd(Long postId) {
        Long userId = JwtUtil.getMemberId();

        Post post = postRepository.findByPostIdAndUserId(postId,userId)
                .orElseThrow(() -> new PostNotFoundException(postId));

        Room room = roomRepository.findById(post.getRoomId())
                .orElseThrow(() -> new RoomNotFoundException(post.getRoomId()));

        post.setIsShown(false);
        room.setIsTradeable(false);

        postRepository.save(post);
        roomRepository.save(room);

        return true;
    }

    @Override
    @Transactional
    public List<PostResponseDto> getTradeEndPost(Integer pages) {
        List<PostResponseDto> responseDtoList = new ArrayList<>();
        Long userId = JwtUtil.getMemberId();

        Sort sort = Sort.by(Sort.Direction.DESC,"createdAt");
        Pageable pageable = PageRequest.of(pages,10,sort);

        List<Post> postList = postRepository.findByUserId(userId,false,pageable);

        for (Post post : postList) {

            Room room = roomRepository.findById(post.getRoomId())
                    .orElseThrow(() -> new RoomNotFoundException(post.getRoomId()));

            Image image = imageRepository.findMainImage(room.getRoomId(),0L)
                    .orElseThrow(() -> new ImageNotFoundException(room.getRoomId()));

            List<Options> optionsList = optionsRepository.findByRoomId(room.getRoomId());

            PostResponseDto responseDto = PostResponseDto.of(post,room,image.getImageUrl(),optionsList);
            responseDtoList.add(responseDto);
        }

        return responseDtoList;
    }
}
