package com.dankan.config;

import com.dankan.repository.UserRepository;
import com.dankan.service.user.UserService;
import com.dankan.service.user.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {
    private final UserRepository userRepository;

    public SpringConfig(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Bean
    public UserService userService() {
        return new UserServiceImpl(userRepository);
    }
}
