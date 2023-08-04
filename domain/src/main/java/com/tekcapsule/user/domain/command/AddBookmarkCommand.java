package com.tekcapsule.user.domain.command;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tekcapsule.core.domain.Command;
import com.tekcapsule.user.domain.model.Bookmark;
import lombok.Builder;
import lombok.Data;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class AddBookmarkCommand extends Command {
    private String userId;
    private Bookmark bookmark;
}
