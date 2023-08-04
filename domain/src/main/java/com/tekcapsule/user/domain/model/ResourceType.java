package com.tekcapsule.user.domain.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConvertedEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum ResourceType {
    ARTICLE("Article"),
    VIDEO("Video"),
    RESEARCH_PAPER("Research Paper"),
    NEWS("News"),
    COURSE("Course"),
    EVENT("Event"),
    INTERVIEW_PREP("Interview Prep"),
    NEWS_DIGEST("News Digest"),
    PRODUCT("Product");

    @Getter
    private String value;
}