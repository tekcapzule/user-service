package com.tekcapsule.userpreference.domain.service;

import com.tekcapsule.userpreference.domain.command.CreateCommand;
import com.tekcapsule.userpreference.domain.command.DisableCommand;
import com.tekcapsule.userpreference.domain.model.Mentor;
import com.tekcapsule.userpreference.domain.query.SearchQuery;
import com.tekcapsule.userpreference.domain.command.UpdateCommand;
import com.tekcapsule.userpreference.domain.query.SearchItem;

import java.util.List;

public interface UserPreferenceService {

    Mentor create(CreateCommand createCommand);

    Mentor update(UpdateCommand updateCommand);

    void disable(DisableCommand disableCommand);

    List<SearchItem> search(SearchQuery searchQuery);

    Mentor get(String tenantId, String userId);
}
