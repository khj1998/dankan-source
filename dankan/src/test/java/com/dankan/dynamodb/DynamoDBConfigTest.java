package com.dankan.dynamodb;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.DeleteTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.util.TableUtils;
import com.dankan.DankanApplication;
import com.dankan.domain.chat.Chatting;
import com.dankan.service.chatting.DynamoDBService;
import com.dankan.vo.ChattingMessage;
import com.dankan.vo.ChattingMessageResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = DankanApplication.class)
public class DynamoDBConfigTest {
    private @Autowired AmazonDynamoDB amazonDynamoDb;
    private @Autowired DynamoDBMapper dynamoDbMapper;
    private @Autowired DynamoDBService dynamoDBService;

    @Test
    void createTable() {
        CreateTableRequest createTableRequest = dynamoDbMapper.generateCreateTableRequest(Chatting.class)
                .withProvisionedThroughput(new ProvisionedThroughput(1L, 1L));


        TableUtils.createTableIfNotExists(amazonDynamoDb, createTableRequest);
    }

    @Test
    void deleteTable() {
        DeleteTableRequest deleteTableRequest = dynamoDbMapper.generateDeleteTableRequest(Chatting.class);
        TableUtils.deleteTableIfExists(amazonDynamoDb, deleteTableRequest);
    }

    @Test
    void test() {
        final List<Chatting> response = dynamoDBService.findMessageHistory(1L);

        assertThat(response.size()).isEqualTo(3);
    }
}
