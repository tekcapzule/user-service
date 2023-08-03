package com.tekcapsule.user.application.function.input;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tekcapsule.user.domain.model.ResourceType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
public class AddBookmarkInput {
    private String userId;
    private String resourceId;
    private ResourceType resourceType;
}
