package com.tekcapzule.user.domain.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConvertedEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum ResourceType {
    VIDEO("VideoLibrary"),
    RESEARCH_PAPER("Research Paper"),
    COURSE("Course"),
    TEKBYTE("Tek Byte");
    @Getter
    private String value;
}