package com.tekcapsule.userpreference.domain.repository;

import in.devstream.core.domain.CrudRepository;
import com.tekcapsule.userpreference.domain.model.Mentor;
import com.tekcapsule.userpreference.domain.query.SearchItem;

import java.util.List;

public interface UserPreferenceDynamoRepository extends CrudRepository<Mentor, String> {

    void disableById(String tenantId, String id);
    List<SearchItem> search(String tenantId);
}
