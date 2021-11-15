package com.tekcapsule.user.domain.service;

import com.tekcapsule.user.domain.command.*;
import com.tekcapsule.user.domain.model.User;


public interface UserService {

    void create(CreateCommand createCommand);

    void update(UpdateCommand updateCommand);

    void disable(DisableCommand disableCommand);

    void addBookmark(AddBookmarkCommand addBookmarkCommand);

    void removeBookmark(RemoveBookmarkCommand removeBookmarkCommand);

    void followTopic(FollowTopicCommand followTopicCommand);

    void unfollowTopic(UnfollowTopicCommand unfollowTopicCommand);

    User get(String userId);
}
