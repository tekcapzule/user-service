package com.tekcapsule.user.domain.repository;

import com.tekcapsule.core.domain.CrudRepository;
import com.tekcapsule.user.domain.model.User;
import com.tekcapsule.user.domain.query.SearchItem;

import java.util.List;

public interface UserDynamoRepository extends CrudRepository<User, String> {

    void disableById(String tenantId, String id);
    List<SearchItem> search(String tenantId);
}
