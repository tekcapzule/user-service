package com.tekcapsule.user.application.function.input;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tekcapsule.user.domain.model.Bookmark;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
public class UpdateInput {
    private String userId;
    private String firstName;
    private String lastName;
    private List<String> subscribedTopics;
    private String emailId;
    private String phoneNumber;
    private List<Bookmark> bookmarks;
}
