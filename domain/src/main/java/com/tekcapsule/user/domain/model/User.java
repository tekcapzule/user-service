package com.tekcapsule.user.domain.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
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
public class User extends BaseDomainEntity<String> implements AggregateRoot {

    @DynamoDBHashKey(attributeName="userId")
    private String userId;
    @DynamoDBRangeKey(attributeName="active")
    private Boolean active;
    @DynamoDBAttribute(attributeName = "firstName")
    private String firstName;
    @DynamoDBAttribute(attributeName = "lastName")
    private String lastName;
    @DynamoDBAttribute(attributeName = "bookmarks")
    private List<String> bookmarks;
    @DynamoDBAttribute(attributeName = "SubscribedTopics")
    private List<String> subscribedTopics;
    @DynamoDBAttribute(attributeName = "emailId")
    private String emailId;
    @DynamoDBAttribute(attributeName = "contactNumber")
    private String contactNumber;
    @DynamoDBAttribute(attributeName = "activeSince")
    private String activeSince;
}