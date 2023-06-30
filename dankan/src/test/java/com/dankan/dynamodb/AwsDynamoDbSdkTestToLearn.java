package com.dankan.dynamodb;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.model.*;
import com.amazonaws.services.dynamodbv2.util.TableUtils;
import com.dankan.domain.chat.Chatting;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.BDDAssertions.then;

@SpringBootTest
class AwsDynamoDbSdkTestToLearn {
    private AmazonDynamoDB amazonDynamoDb;
    private DynamoDBMapper dynamoDbMapper;
    @Value("${amazon.aws.accessKey}")
    private String accessKey;

    @Value("${amazon.aws.secretKey}")
    private String secretKey;

    @Value("${amazon.dynamodb.endpoint}")
    private String endpoint;

    @Value("${amazon.dynamodb.region}")
    private String region;

    Map<String, AttributeValue> item;

    @BeforeEach
    void setup() {
        AWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
        AWSCredentialsProvider awsCredentialsProvider = new AWSStaticCredentialsProvider(awsCredentials);
        AwsClientBuilder.EndpointConfiguration endpointConfiguration = new AwsClientBuilder.EndpointConfiguration(endpoint, region);

        amazonDynamoDb = AmazonDynamoDBClientBuilder.standard()
                .withCredentials(awsCredentialsProvider)
                  .withEndpointConfiguration(endpointConfiguration).build();

        item = new HashMap<>();

        dynamoDbMapper = new DynamoDBMapper(amazonDynamoDb, DynamoDBMapperConfig.DEFAULT);
    }

    @Test
    void 테이블_만들기() {
        /**
         * private String roomId;
         *     private String memberId;
         *     private String publishedAt;
         *     private String message;
         * */
        CreateTableRequest createTableRequest = (new CreateTableRequest())
                .withAttributeDefinitions(
                        new AttributeDefinition("published_at", ScalarAttributeType.S),
                        new AttributeDefinition("room_id", ScalarAttributeType.S)
                ).withTableName("Comment1").withKeySchema(
                        new KeySchemaElement("room_id", KeyType.HASH),
                        new KeySchemaElement("published_at", KeyType.RANGE)
                ).withProvisionedThroughput(
                        new ProvisionedThroughput(1L, 1L)
                );

        boolean hasTableBeenCreated = TableUtils.createTableIfNotExists(amazonDynamoDb, createTableRequest);
        then(hasTableBeenCreated).isTrue();
    }

    @Test
    void 객체_테이블_생성() {
        CreateTableRequest createTableRequest = dynamoDbMapper.generateCreateTableRequest(Chatting.class)
                .withProvisionedThroughput(new ProvisionedThroughput(1L, 1L));

        then(TableUtils.createTableIfNotExists(amazonDynamoDb, createTableRequest)).isTrue();
    }

    @Test
    void 튜플_추가() {
        /**
         *     private String roomId;
         *     private String memberId;
         *     private String publishedAt;
         *     private String message;
         * */
        item.put("room_id", (new AttributeValue()).withS("1"));
        item.put("member_id", (new AttributeValue()).withS("1"));
        item.put("message", (new AttributeValue()).withS("comment content"));
        item.put("published_at", (new AttributeValue()).withS(LocalDateTime.now().toString()));

        PutItemRequest putItemRequest = (new PutItemRequest())
                .withTableName("Comment1")
                .withItem(item);

        PutItemResult putItemResult = amazonDynamoDb.putItem(putItemRequest);
        then(putItemResult.getSdkHttpMetadata().getHttpStatusCode()).isEqualTo(200);
    }

    @Test
    void 항목_조회() {
        Map<String, AttributeValue> key = new HashMap<>();
        key.put("room_id", (new AttributeValue()).withS("1"));

        GetItemRequest getItemRequest = (new GetItemRequest())
                .withTableName("Comment1")
                .withKey(key);

        GetItemResult getItemResult = amazonDynamoDb.getItem(getItemRequest);

        then(getItemResult.getItem()).containsAllEntriesOf(item);
    }
}