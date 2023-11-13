package com.tekcapzule.user.domain.service;

import com.tekcapzule.user.domain.command.*;
import com.tekcapzule.user.domain.command.*;
import com.tekcapzule.user.domain.model.User;


public interface UserService {

    void create(CreateCommand createCommand);

    void update(UpdateCommand updateCommand);

    void disable(DisableCommand disableCommand);

    void addBookmark(AddBookmarkCommand addBookmarkCommand);

    void removeBookmark(RemoveBookmarkCommand removeBookmarkCommand);

    void followTopic(FollowTopicCommand followTopicCommand);

    void unfollowTopic(UnfollowTopicCommand unfollowTopicCommand);

    User get(String userId);

    int getAllUsersCount();
}
