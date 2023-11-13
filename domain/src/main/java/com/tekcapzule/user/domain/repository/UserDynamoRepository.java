package com.tekcapzule.user.domain.repository;

import com.tekcapzule.core.domain.CrudRepository;
import com.tekcapzule.user.domain.model.User;


public interface UserDynamoRepository extends CrudRepository<User, String> {
    int getAllUsersCount();

}
