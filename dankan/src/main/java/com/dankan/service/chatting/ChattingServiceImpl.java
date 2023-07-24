package com.dankan.service.chatting;

import com.dankan.repository.UserRepository;
import com.dankan.vo.UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;

@RequiredArgsConstructor
@Slf4j
public class ChattingServiceImpl implements ChattingService {
    private final UserRepository userRepository;

    @Cacheable(key = "#id", value = "userInfo")
    public UserInfo getInfo(Long id) {
        return userRepository.findName(id).orElseThrow(
                () -> new RuntimeException()
        );
    }
}
