package com.tekcapzule.user.application.function.input;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tekcapzule.user.domain.model.Bookmark;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
public class AddBookmarkInput {
    private String userId;
    private Bookmark bookmark;
}
