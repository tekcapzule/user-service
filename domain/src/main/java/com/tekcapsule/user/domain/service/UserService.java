package com.tekcapsule.user.domain.service;

import com.tekcapsule.user.domain.command.CreateCommand;
import com.tekcapsule.user.domain.command.DisableCommand;
import com.tekcapsule.user.domain.model.User;
import com.tekcapsule.user.domain.command.UpdateCommand;


public interface UserService {

    User create(CreateCommand createCommand);

    User update(UpdateCommand updateCommand);

    void disable(DisableCommand disableCommand);

    User get(String userId);
}
