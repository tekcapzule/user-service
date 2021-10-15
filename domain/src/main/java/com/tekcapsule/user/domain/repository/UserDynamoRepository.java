package com.tekcapsule.user.domain.repository;

import com.tekcapsule.core.domain.CrudRepository;
import com.tekcapsule.user.domain.model.User;


public interface UserDynamoRepository extends CrudRepository<User, String> {

    void disable( String id);
}
