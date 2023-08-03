package com.tekcapsule.user.domain.command;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tekcapsule.core.domain.Command;
import com.tekcapsule.user.domain.model.PhoneNumber;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class CreateCommand extends Command {
    private String firstName;
    private String lastName;
    private List<String> subscribedTopics;
    private String emailId;
    private PhoneNumber phoneNumber;
}


