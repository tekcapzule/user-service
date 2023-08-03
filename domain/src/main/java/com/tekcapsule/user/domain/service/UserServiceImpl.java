package com.tekcapsule.user.domain.service;

import com.tekcapsule.user.domain.command.*;
import com.tekcapsule.user.domain.model.PhoneNumber;
import com.tekcapsule.user.domain.model.Status;
import com.tekcapsule.user.domain.repository.UserDynamoRepository;
import com.tekcapsule.user.domain.model.User;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public void create(CreateCommand createCommand) {

        log.info(String.format("Entering create user service - User Id:%s", createCommand.getEmailId()));

        User user = User.builder()
                .userId(createCommand.getEmailId())
                .emailId(createCommand.getEmailId())
                .firstName(createCommand.getFirstName())
                .lastName(createCommand.getLastName())
                .phoneNumber(createCommand.getPhoneNumber())
                .subscribedTopics(createCommand.getSubscribedTopics())
                .activeSince(DateTime.now(DateTimeZone.UTC).toString())
                .status(Status.ACTIVE)
                .build();

        user.setAddedOn(createCommand.getExecOn());
        user.setUpdatedOn(createCommand.getExecOn());
        user.setAddedBy(createCommand.getExecBy().getUserId());

        userDynamoRepository.save(user);
    }

    @Override
    public void update(UpdateCommand updateCommand) {

        log.info(String.format("Entering update user service - User Id:%s", updateCommand.getUserId()));

        User user = userDynamoRepository.findBy(updateCommand.getUserId());
        if (user != null) {
            user.setEmailId(updateCommand.getEmailId());
            user.setFirstName(updateCommand.getFirstName());
            user.setLastName(updateCommand.getLastName());
            user.setPhoneNumber(updateCommand.getPhoneNumber());
            user.setSubscribedTopics(updateCommand.getSubscribedTopics());
            user.setBookmarks(updateCommand.getBookmarks());
            user.setUpdatedOn(updateCommand.getExecOn());
            user.setUpdatedBy(updateCommand.getExecBy().getUserId());
            userDynamoRepository.save(user);
        }
    }

    @Override
    public void disable(DisableCommand disableCommand) {

        log.info(String.format("Entering disable user service - User Id:%s", disableCommand.getUserId()));

        User user = userDynamoRepository.findBy(disableCommand.getUserId());
        if (user != null) {

            user.setStatus(Status.INACTIVE);

            user.setUpdatedOn(disableCommand.getExecOn());
            user.setUpdatedBy(disableCommand.getExecBy().getUserId());

            userDynamoRepository.save(user);
        }
    }

    @Override
    public void addBookmark(AddBookmarkCommand addBookmarkCommand) {

        log.info(String.format("Entering add bookmark service - User Id:%s, Capsule Id:%s", addBookmarkCommand.getUserId(), addBookmarkCommand.getCapsuleId()));

        User user = userDynamoRepository.findBy(addBookmarkCommand.getUserId());
        if (user != null) {

            List<String> bookMarks = new ArrayList<>();
            if (user.getBookmarks() != null) {
                bookMarks = user.getBookmarks();
            }
            bookMarks.add(addBookmarkCommand.getCapsuleId());
            user.setBookmarks(bookMarks);

            user.setUpdatedOn(addBookmarkCommand.getExecOn());
            user.setUpdatedBy(addBookmarkCommand.getExecBy().getUserId());

            userDynamoRepository.save(user);
        }
    }

    @Override
    public void removeBookmark(RemoveBookmarkCommand removeBookmarkCommand) {

        log.info(String.format("Entering remove bookmark service - User Id:%s, Capsule Id:%s", removeBookmarkCommand.getUserId(), removeBookmarkCommand.getCapsuleId()));

        User user = userDynamoRepository.findBy(removeBookmarkCommand.getUserId());
        if (user != null) {

            List<String> bookMarks = new ArrayList<>();
            if (user.getBookmarks() != null) {
                bookMarks = user.getBookmarks();
            }
            bookMarks.remove(removeBookmarkCommand.getCapsuleId());
            user.setBookmarks(bookMarks);

            user.setUpdatedOn(removeBookmarkCommand.getExecOn());
            user.setUpdatedBy(removeBookmarkCommand.getExecBy().getUserId());

            userDynamoRepository.save(user);
        }
    }

    @Override
    public void followTopic(FollowTopicCommand followTopicCommand) {
        log.info(String.format("Entering follow topic service - User Id:%s, Topic Code:%s", followTopicCommand.getUserId(), followTopicCommand.getTopicCodes()));

        User user = userDynamoRepository.findBy(followTopicCommand.getUserId());
        if (user != null) {

            List<String> followedTopics = new ArrayList<>();
            followedTopics.addAll(followTopicCommand.getTopicCodes());
            user.setSubscribedTopics(followedTopics);

            user.setUpdatedOn(followTopicCommand.getExecOn());
            user.setUpdatedBy(followTopicCommand.getExecBy().getUserId());

            userDynamoRepository.save(user);
        }
    }

    @Override
    public void unfollowTopic(UnfollowTopicCommand unfollowTopicCommand) {
        log.info(String.format("Entering unfollow topic service - User Id:%s, Topic Code:%s", unfollowTopicCommand.getUserId(), unfollowTopicCommand.getTopicCodes()));

        User user = userDynamoRepository.findBy(unfollowTopicCommand.getUserId());
        if (user != null) {

            List<String> followedTopics = new ArrayList<>();
            if (user.getSubscribedTopics() != null) {
                followedTopics = user.getSubscribedTopics();
            }

            followedTopics.removeAll(unfollowTopicCommand.getTopicCodes());
            user.setSubscribedTopics(followedTopics);

            user.setUpdatedOn(unfollowTopicCommand.getExecOn());
            user.setUpdatedBy(unfollowTopicCommand.getExecBy().getUserId());

            userDynamoRepository.save(user);
        }
    }

    @Override
    public User get(String userId) {

        log.info(String.format("Entering get user service - User Id:%s", userId));

        return userDynamoRepository.findBy(userId);
    }

    @Override
    public int getAllUsersCount() {
        log.info("Entering getall users count service");
        return userDynamoRepository.getAllUsersCount();
    }


}
