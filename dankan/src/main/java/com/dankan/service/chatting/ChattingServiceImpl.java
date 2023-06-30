package com.dankan.service.chatting;

import com.dankan.domain.chat.Chatting;
import com.dankan.dto.response.chatting.ChattingLogResponseDto;
import com.dankan.repository.UserRepository;
import com.dankan.vo.ChattingMessageResponse;
import com.dankan.vo.UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class ChattingServiceImpl implements ChattingService {
    private final UserRepository userRepository;
    private final DynamoDBService dynamoDBService;

    @Override
    public List<ChattingLogResponseDto> getHistory(final Long id) {
        return convert(dynamoDBService.findMessageHistory(id));
    }

    private List<ChattingLogResponseDto> convert(List<Chatting> chattings) {
        List<ChattingLogResponseDto> list = new ArrayList<>();

        for(Chatting chatting : chattings) {
            UserInfo info = userRepository.findName(Long.parseLong(chatting.getMemberId()));
            list.add(
                    ChattingLogResponseDto.builder()
                            .roomId(chatting.getRoomId())
                            .message(chatting.getMessage())
                            .publishedAt(chatting.getPublishedAt())
                            .senderName(info.getNickname())
                            .imgUrl(info.getProfileImg())
                            .build()
            );
        }

        return null;
    }
}
