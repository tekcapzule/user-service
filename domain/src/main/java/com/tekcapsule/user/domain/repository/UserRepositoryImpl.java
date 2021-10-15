package com.tekcapsule.user.domain.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.tekcapsule.user.domain.model.User;
import com.tekcapsule.user.domain.query.SearchItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Repository
public class UserRepositoryImpl implements UserDynamoRepository {

    private DynamoDBMapper dynamo;

    @Autowired
    public UserRepositoryImpl(DynamoDBMapper dynamo) {
        this.dynamo = dynamo;
    }

    @Override
    public List<User> findAll(String tenantId) {

        User hashKey = User.builder().tenantId(tenantId).build();
        DynamoDBQueryExpression<User> queryExpression = new DynamoDBQueryExpression<User>()
                .withHashKeyValues(hashKey);

        return dynamo.query(User.class, queryExpression);
    }

    @Override
    public User findBy(String tenantId, String userId) {
        return dynamo.load(User.class, tenantId, userId);
    }

    @Override
    public User save(User user) {
        dynamo.save(user);
        return user;
    }

    @Override
    public void delete(String tenantId, String id) {
        User user = findBy(tenantId, id);
        if (user != null) {
            dynamo.delete(user);
        }
    }

    @Override
    public void disableById(String tenantId, String id) {
        User user = findBy(tenantId, id);
        if (user != null) {
            user.setActive(false);
            dynamo.save(user);
        }
    }

    @Override
    public List<SearchItem> search(String tenantId) {
        User hashKey = User.builder().tenantId(tenantId).build();
        DynamoDBQueryExpression<User> queryExpression = new DynamoDBQueryExpression<User>()
                .withHashKeyValues(hashKey);
        List<User> users = dynamo.query(User.class, queryExpression);
        List<SearchItem> searchItems = new ArrayList<SearchItem>();
        if (users != null) {
            searchItems = users.stream().map(user -> {
                return SearchItem.builder()
                        .activeSince(user.getActiveSince())
                        .headLine(user.getHeadLine())
                        .name(user.getName())
                        .photoUrl(user.getPhotoUrl())
                        .rating(user.getRating())
                        .social(user.getSocial())
                        .build();
            }).collect(Collectors.toList());
        }
        return searchItems;
    }
}
