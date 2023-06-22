package com.dankan.config;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.dankan.repository.TokenRepository;
import com.dankan.repository.UserRepository;
import com.dankan.service.s3.S3UploadService;
import com.dankan.service.s3.S3UploaderServiceImpl;
import com.dankan.service.sms.SmsService;
import com.dankan.service.sms.SmsServiceImpl;
import com.dankan.service.token.TokenService;
import com.dankan.service.token.TokenServiceImpl;
import com.dankan.service.user.UserService;
import com.dankan.service.user.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final AmazonS3 amazonS3Client;

    public SpringConfig(final UserRepository userRepository, final AmazonS3 amazonS3Client,final TokenRepository tokenRepository) {
        this.userRepository = userRepository;
        this.amazonS3Client = amazonS3Client;
        this.tokenRepository = tokenRepository;
    }

    @Bean
    public UserService userService() {
        return new UserServiceImpl(userRepository,tokenRepository);
    }

    @Bean
    public S3UploadService S3UploadService() {
        return new S3UploaderServiceImpl(amazonS3Client);
    }

    @Bean
    public TokenService tokenService() {
        return new TokenServiceImpl(tokenRepository, userRepository);
    }

    @Bean
    public SmsService smsService() {
        return new SmsServiceImpl();
    }
}
