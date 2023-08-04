package com.tekcapsule.user.domain.command;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tekcapsule.core.domain.Command;
import com.tekcapsule.user.domain.model.Bookmark;
import com.tekcapsule.user.domain.model.PhoneNumber;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class UpdateCommand extends Command {
    private String userId;
    private String firstName;
    private String lastName;
    private String emailId;
    private PhoneNumber phoneNumber;
    private List<String> subscribedTopics;
    private List<Bookmark> bookmarks;
}
