package com.tekcapsule.user.domain.service;

import com.tekcapsule.user.domain.command.*;
import com.tekcapsule.user.domain.model.User;


public interface UserService {

    User create(CreateCommand createCommand);

    User update(UpdateCommand updateCommand);

    void disable(DisableCommand disableCommand);

    void addBookmark(AddBookmarkCommand addBookmarkCommand);

    void followTopic(FollowTopicCommand followTopicCommand);

    User get(String userId);
}
