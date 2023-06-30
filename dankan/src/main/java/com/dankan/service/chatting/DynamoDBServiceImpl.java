package com.dankan.service.chatting;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.util.TableUtils;
import com.dankan.domain.chat.Chatting;
import com.dankan.dto.response.chatting.ChattingLogResponseDto;
import com.dankan.exception.chatting.ChattingRoomDataNotExistException;
import com.dankan.vo.ChattingMessageResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class DynamoDBServiceImpl implements DynamoDBService {
    private final DynamoDBMapper dynamoDbMapper;
    private final AmazonDynamoDB amazonDynamoDB;

    @Override
    public Boolean createTable() {
        CreateTableRequest createTableRequest = dynamoDbMapper.generateCreateTableRequest(Chatting.class)
                .withProvisionedThroughput(new ProvisionedThroughput(1L, 1L));


        return TableUtils.createTableIfNotExists(amazonDynamoDB, createTableRequest);
    }

    @Override
    public void save(ChattingMessageResponse chatting) {
        dynamoDbMapper.save(
                Chatting.builder()
                    .roomId(chatting.getRoomId())
                    .publishedAt(chatting.getPublishedAt())
                    .message(chatting.getMessage())
                    .memberId(chatting.getMemberId())
                .build()
        );
    }

    @Override
    public List<Chatting> findMessageHistory(final Long id) {
        Chatting target = Chatting.builder()
                .roomId(id.toString())
                .build();

        DynamoDBQueryExpression<Chatting> queryExpression = new DynamoDBQueryExpression<Chatting>()
                .withHashKeyValues(target);

        List<Chatting> itemList = dynamoDbMapper.query(Chatting.class, queryExpression);

        log.info("itemList: {}", itemList.size());

        if(itemList.size() == 0) {
            throw new ChattingRoomDataNotExistException(id.toString());
        }

        return itemList;
    }
}
