package com.tekcapsule.user.domain.model;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tekcapsule.core.domain.AggregateRoot;
import com.tekcapsule.core.domain.BaseDomainEntity;
import lombok.*;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode(callSuper = true)
@DynamoDBTable(tableName = "User")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseDomainEntity implements AggregateRoot {

    @DynamoDBHashKey(attributeName="userId")
    private String userId;
    @DynamoDBAttribute(attributeName = "emailId")
    private String emailId;
    @DynamoDBAttribute(attributeName = "firstName")
    private String firstName;
    @DynamoDBAttribute(attributeName = "lastName")
    private String lastName;
    @DynamoDBAttribute(attributeName = "bookmarks")
    private List<Bookmark> bookmarks;
    @DynamoDBAttribute(attributeName = "SubscribedTopics")
    private List<String> subscribedTopics;
    @DynamoDBAttribute(attributeName = "phoneNumber")
    private String phoneNumber;
    @DynamoDBAttribute(attributeName = "activeSince")
    private String activeSince;
    @DynamoDBAttribute(attributeName = "status")
    @DynamoDBTypeConvertedEnum
    private Status status;
}