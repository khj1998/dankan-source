package com.dankan.config;

import com.amazonaws.services.s3.AmazonS3;
import com.dankan.repository.*;
import com.dankan.service.post.PostService;
import com.dankan.service.post.PostServiceImpl;
import com.dankan.service.report.ReportService;
import com.dankan.service.report.ReportServiceImpl;
import com.dankan.service.review.ReviewService;
import com.dankan.service.review.ReviewServiceImpl;
import com.dankan.service.room.RoomService;
import com.dankan.service.room.RoomServiceImpl;
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
    private final PostRepository postRepository;
    private final AmazonS3 amazonS3Client;
    private final RoomRepository roomRepository;
    private final RoomImageRepository roomImageRepository;
    private final PostHeartRepository postHeartRepository;
    private final PostReportRepository postReportRepository;
    private final ReviewRepository reviewRepository;
    private final ReviewReportRepository reviewReportRepository;
    private final RecentWatchRepository recentWatchRepository;
    private final UnivRepository univRepository;
    private final JavaMailSender javaMailSender;
    private final String mail;

    public SpringConfig(final UserRepository userRepository, final AmazonS3 amazonS3Client, final TokenRepository tokenRepository
                        , final PostRepository postRepository
                        , final RoomRepository roomRepository
                        , final RoomImageRepository roomImageRepository
                        , final PostHeartRepository postHeartRepository
                        , final PostReportRepository postReportRepository
                        , final ReviewRepository reviewRepository
                        , final ReviewReportRepository reviewReportRepository, final UnivRepository univRepository, final JavaMailSender javaMailSender, @Value("${mail.id}") String mail

                        , final RecentWatchRepository recentWatchRepository) {
        this.userRepository = userRepository;
        this.amazonS3Client = amazonS3Client;
        this.tokenRepository = tokenRepository;
        this.postRepository = postRepository;
        this.roomRepository = roomRepository;
        this.roomImageRepository = roomImageRepository;
        this.postHeartRepository = postHeartRepository;
        this.postReportRepository = postReportRepository;
        this.reviewRepository = reviewRepository;
        this.reviewReportRepository = reviewReportRepository;
        this.recentWatchRepository = recentWatchRepository;
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
    public PostService postService() {
        return new PostServiceImpl(postRepository,roomRepository,postHeartRepository,recentWatchRepository,roomImageRepository);
    }

    @Bean
    public RoomService roomService() {
        return new RoomServiceImpl(roomImageRepository);
    }

    @Bean
    public ReportService reportService() {
        return new ReportServiceImpl(postReportRepository,reviewReportRepository,postRepository,roomRepository,reviewRepository);
    }

    @Bean
    public ReviewService reviewService() {
        return new ReviewServiceImpl(userRepository, reviewRepository, roomRepository,roomImageRepository);
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
