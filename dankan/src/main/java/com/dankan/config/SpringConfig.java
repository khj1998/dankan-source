package com.dankan.config;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.s3.AmazonS3;
import com.dankan.repository.*;
import com.dankan.service.chatting.ChattingService;
import com.dankan.service.chatting.ChattingServiceImpl;
import com.dankan.service.chatting.DynamoDBService;
import com.dankan.service.chatting.DynamoDBServiceImpl;
import com.dankan.service.post.PostService;
import com.dankan.service.post.PostServiceImpl;
import com.dankan.service.report.ReportService;
import com.dankan.service.report.ReportServiceImpl;
import com.dankan.service.review.ReviewService;
import com.dankan.service.review.ReviewServiceImpl;
import com.dankan.service.image.ImageService;
import com.dankan.service.image.ImageServiceImpl;
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
    private final PostHeartRepository postHeartRepository;
    private final PostReportRepository postReportRepository;
    private final ReviewRepository reviewRepository;
    private final ReviewReportRepository reviewReportRepository;
    private final RecentWatchRepository recentWatchRepository;
    private final UnivRepository univRepository;
    private final JavaMailSender javaMailSender;
    private final String mail;
    private final DynamoDBMapper dynamoDBMapper;
    private final AmazonDynamoDB amazonDynamoDB;
    private final DateLogRepository dateLogRepository;
    private final OptionsRepository optionsRepository;
    private final ImageRepository imageRepository;

    public SpringConfig(final UserRepository userRepository, final AmazonS3 amazonS3Client, final TokenRepository tokenRepository
                        , final PostRepository postRepository
                        , final RoomRepository roomRepository
                        , final PostHeartRepository postHeartRepository
                        , final PostReportRepository postReportRepository
                        , final ReviewRepository reviewRepository
                        , final ReviewReportRepository reviewReportRepository, final UnivRepository univRepository, final JavaMailSender javaMailSender, @Value("${mail.id}") String mail, final DateLogRepository dateLogRepository
                        , final RecentWatchRepository recentWatchRepository
                        , final OptionsRepository optionsRepository
                        , final ImageRepository imageRepository
                        , final AmazonDynamoDB amazonDynamoDB
                        , final DynamoDBMapper dynamoDBMapper){
        this.userRepository = userRepository;
        this.amazonS3Client = amazonS3Client;
        this.tokenRepository = tokenRepository;
        this.postRepository = postRepository;
        this.roomRepository = roomRepository;
        this.postHeartRepository = postHeartRepository;
        this.postReportRepository = postReportRepository;
        this.reviewRepository = reviewRepository;
        this.reviewReportRepository = reviewReportRepository;
        this.recentWatchRepository = recentWatchRepository;
        this.univRepository = univRepository;
        this.javaMailSender = javaMailSender;
        this.mail = mail;
        this.dynamoDBMapper = dynamoDBMapper;
        this.amazonDynamoDB = amazonDynamoDB;
        this.dateLogRepository = dateLogRepository;
        this.optionsRepository = optionsRepository;
        this.imageRepository = imageRepository;
    }

    @Bean
    public UserService userService() {
        return new UserServiceImpl(userRepository,tokenRepository, dateLogRepository);
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
        return new PostServiceImpl(postRepository,roomRepository,postHeartRepository,dateLogRepository,recentWatchRepository,optionsRepository,imageRepository);
    }

    @Bean
    public ImageService roomService() {
        return new ImageServiceImpl(postRepository,imageRepository,reviewRepository);
    }

    @Bean
    public ReportService reportService() {
        return new ReportServiceImpl(postReportRepository,reviewReportRepository,postRepository,roomRepository,reviewRepository,dateLogRepository);
    }

    @Bean
    public ReviewService reviewService() {
        return new ReviewServiceImpl(userRepository, reviewRepository, roomRepository,dateLogRepository,optionsRepository,imageRepository);
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

    @Bean
    public DynamoDBService dynamoDBService() {
        return new DynamoDBServiceImpl(dynamoDBMapper, amazonDynamoDB);
    }

    @Bean
    public ChattingService chattingService() {
        return new ChattingServiceImpl(userRepository);
    }
}
