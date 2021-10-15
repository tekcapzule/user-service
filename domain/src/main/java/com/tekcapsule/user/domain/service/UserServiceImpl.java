package com.tekcapsule.user.domain.service;

import com.tekcapsule.user.domain.repository.UserDynamoRepository;
import com.tekcapsule.user.domain.command.CreateCommand;
import com.tekcapsule.user.domain.command.DisableCommand;
import com.tekcapsule.user.domain.command.UpdateCommand;
import com.tekcapsule.user.domain.model.User;
import com.tekcapsule.user.domain.query.SearchItem;
import com.tekcapsule.user.domain.query.SearchQuery;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

        log.info(String.format("Entering create mentor service - Tenant Id:{0}, Name:{1}", createCommand.getTenantId(), createCommand.getName().toString()));

        User user = User.builder()
                .active(true)
                .activeSince(DateTime.now(DateTimeZone.UTC).toString())
                .blocked(false)
                .awards(createCommand.getAwards())
                .certifications(createCommand.getCertifications())
                .contact(createCommand.getContact())
                .build();

        user.setAddedOn(createCommand.getExecOn());
        user.setUpdatedOn(createCommand.getExecOn());
        user.setAddedBy(createCommand.getExecBy().getUserId());

        return userDynamoRepository.save(user);
    }

    @Override
    public User update(UpdateCommand updateCommand) {

        log.info(String.format("Entering update mentor service - Tenant Id:{0}, User Id:{1}", updateCommand.getTenantId(), updateCommand.getUserId()));

        User user = userDynamoRepository.findBy(updateCommand.getTenantId(), updateCommand.getUserId());
        if (user != null) {
            user.setAwards(updateCommand.getAwards());
            user.setHeadLine(updateCommand.getHeadLine());
            user.setContact(updateCommand.getContact());

            user.setUpdatedOn(updateCommand.getExecOn());
            user.setUpdatedBy(updateCommand.getExecBy().getUserId());

            userDynamoRepository.save(user);
        }
        return user;
    }

    @Override
    public void disable(DisableCommand disableCommand) {

        log.info(String.format("Entering disable mentor service - Tenant Id:{0}, User Id:{1}", disableCommand.getTenantId(), disableCommand.getUserId()));

        userDynamoRepository.disableById(disableCommand.getTenantId(), disableCommand.getUserId());
    }

    @Override
    public List<SearchItem> search(SearchQuery searchQuery) {

        log.info(String.format("Entering search mentor service - Tenant Id:{0}", searchQuery.getTenantId()));

        return userDynamoRepository.search(searchQuery.getTenantId());
    }

    @Override
    public User get(String tenantId, String userId) {

        log.info(String.format("Entering get mentor service - Tenant Id:{0}, User Id:{1}", tenantId, userId));

        return userDynamoRepository.findBy(tenantId, userId);
    }
}
