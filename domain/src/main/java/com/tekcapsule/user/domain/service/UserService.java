package com.tekcapsule.user.domain.service;

import com.tekcapsule.user.domain.command.CreateCommand;
import com.tekcapsule.user.domain.command.DisableCommand;
import com.tekcapsule.user.domain.model.User;
import com.tekcapsule.user.domain.query.SearchQuery;
import com.tekcapsule.user.domain.command.UpdateCommand;
import com.tekcapsule.user.domain.query.SearchItem;

import java.util.List;

public interface UserService {

    User create(CreateCommand createCommand);

    User update(UpdateCommand updateCommand);

    void disable(DisableCommand disableCommand);

    List<SearchItem> search(SearchQuery searchQuery);

    User get(String tenantId, String userId);
}
