package com.tekcapsule.user.domain.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.tekcapsule.user.domain.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
public class UserRepositoryImpl implements UserDynamoRepository {

    private DynamoDBMapper dynamo;

    @Autowired
    public UserRepositoryImpl(DynamoDBMapper dynamo) {
        this.dynamo = dynamo;
    }

    @Override
    public List<User> findAll() {

        return dynamo.scan(User.class,new DynamoDBScanExpression());
    }

    @Override
    public User findBy( String userId) {
        return dynamo.load(User.class, userId);
    }

    @Override
    public User save(User user) {
        dynamo.save(user);
        return user;
    }

    @Override
    public void disable( String id) {
        User user = findBy( id);
        if (user != null) {
            user.setActive(false);
            dynamo.save(user);
        }
    }
}
