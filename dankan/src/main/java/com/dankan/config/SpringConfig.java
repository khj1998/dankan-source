package com.dankan.config;

import com.amazonaws.services.s3.AmazonS3;
import com.dankan.repository.TokenRepository;
import com.dankan.repository.UnivRepository;
import com.dankan.repository.UserRepository;
import com.dankan.service.email.EmailService;
import com.dankan.service.email.EmailServiceImpl;
import com.dankan.service.s3.S3UploadService;
import com.dankan.service.s3.S3UploaderServiceImpl;
import com.dankan.service.sms.SmsService;
import com.dankan.service.sms.SmsServiceImpl;
import com.dankan.service.token.TokenService;
import com.dankan.service.token.TokenServiceImpl;
import com.dankan.service.univ.UnivService;
import com.dankan.service.univ.UnivServiceImpl;
import com.dankan.service.sms.SmsService;
import com.dankan.service.sms.SmsServiceImpl;
import com.dankan.service.user.UserService;
import com.dankan.service.user.UserServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;

@Configuration
public class SpringConfig {
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final AmazonS3 amazonS3Client;
    private final UnivRepository univRepository;
    private final JavaMailSender javaMailSender;
    private final String mail;

    public SpringConfig(final UserRepository userRepository, final AmazonS3 amazonS3Client, final TokenRepository tokenRepository, final UnivRepository univRepository, final JavaMailSender javaMailSender, @Value("${mail.id}") String mail) {
        this.userRepository = userRepository;
        this.amazonS3Client = amazonS3Client;
        this.tokenRepository = tokenRepository;
        this.univRepository = univRepository;
        this.javaMailSender = javaMailSender;
        this.mail = mail;
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
    public UnivService univService() {
        return new UnivServiceImpl(univRepository);
    }

    @Bean
    public SmsService smsService() {
        return new SmsServiceImpl(userRepository);
    }

    @Bean
    public EmailService emailService() {
        return new EmailServiceImpl(javaMailSender, mail, userRepository);

    }
}
