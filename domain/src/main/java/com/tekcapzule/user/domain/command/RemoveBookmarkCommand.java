package com.tekcapzule.user.domain.command;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tekcapzule.core.domain.Command;
import com.tekcapzule.user.domain.model.Bookmark;
import lombok.Builder;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class RemoveBookmarkCommand extends Command {
    private String userId;
    private Bookmark bookmark;
}
