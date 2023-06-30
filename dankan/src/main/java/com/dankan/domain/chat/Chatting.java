package com.dankan.domain.chat;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import lombok.*;

@Getter
@Setter // Setters are used in aws-dynamodb-sdk
@NoArgsConstructor
@DynamoDBTable(tableName = "chatting")
@Builder
@AllArgsConstructor
public class Chatting {
    @DynamoDBHashKey(attributeName = "room_id")
    @DynamoDBAttribute
    private String roomId;

    @DynamoDBAttribute
    private String memberId;

    @DynamoDBRangeKey(attributeName = "published_at")
    @DynamoDBAttribute
    private String publishedAt;

    @DynamoDBAttribute
    private String message;

}
