package com.tekcapsule.user.application.function.input;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tekcapsule.user.domain.model.PhoneNumber;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
public class CreateInput {
    private String userId;
    private String firstName;
    private String lastName;
    private String emailId;
    private PhoneNumber phoneNumber;
    private List<String> subscribedTopics;
    private List<String> bookmarks;
}
