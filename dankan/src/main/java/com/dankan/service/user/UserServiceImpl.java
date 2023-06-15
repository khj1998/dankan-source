package com.dankan.service.user;

import com.dankan.domain.User;
import com.dankan.exception.code.ValidErrorCode;
import com.dankan.repository.UserRepository;
import com.dankan.exception.user.UserException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public boolean checkDuplicatedName(String name) {
        final Optional<User> user = userRepository.findByNickname(name);

        user.orElseThrow(() -> new UserException(ValidErrorCode.NICKNAME_ALREADY_EXIST));

        return true;
    }
}
