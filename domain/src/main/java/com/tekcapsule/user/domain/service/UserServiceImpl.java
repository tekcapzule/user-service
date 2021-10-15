package com.tekcapsule.user.domain.service;

import com.tekcapsule.user.domain.repository.UserDynamoRepository;
import com.tekcapsule.user.domain.command.CreateCommand;
import com.tekcapsule.user.domain.command.DisableCommand;
import com.tekcapsule.user.domain.command.UpdateCommand;
import com.tekcapsule.user.domain.model.User;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class UserServiceImpl implements UserService {
    private UserDynamoRepository userDynamoRepository;

    @Autowired
    public UserServiceImpl(UserDynamoRepository userDynamoRepository) {
        this.userDynamoRepository = userDynamoRepository;
    }

    @Override
    public User create(CreateCommand createCommand) {

        log.info(String.format("Entering create user service - User:{0}", createCommand.getUserId()));

        User user = User.builder()
                .active(true)
                .activeSince(DateTime.now(DateTimeZone.UTC).toString())
                .build();

        user.setAddedOn(createCommand.getExecOn());
        user.setUpdatedOn(createCommand.getExecOn());
        user.setAddedBy(createCommand.getExecBy().getUserId());

        return userDynamoRepository.save(user);
    }

    @Override
    public User update(UpdateCommand updateCommand) {

        log.info(String.format("Entering update user service - User Id:{0}", updateCommand.getUserId()));

        User user = userDynamoRepository.findBy(updateCommand.getUserId());
        if (user != null) {
            user.setActive(true);

            user.setUpdatedOn(updateCommand.getExecOn());
            user.setUpdatedBy(updateCommand.getExecBy().getUserId());

            userDynamoRepository.save(user);
        }
        return user;
    }

    @Override
    public void disable(DisableCommand disableCommand) {

        log.info(String.format("Entering disable user service - User Id:{0}", disableCommand.getUserId()));

        userDynamoRepository.disable(disableCommand.getUserId());
    }

    @Override
    public User get(String userId) {

        log.info(String.format("Entering get user service - User Id:{10}", userId));

        return userDynamoRepository.findBy( userId);
    }
}
