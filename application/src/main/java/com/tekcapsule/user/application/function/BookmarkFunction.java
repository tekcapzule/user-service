package com.tekcapsule.user.application.function;

import com.tekcapsule.core.domain.Origin;
import com.tekcapsule.core.utils.HeaderUtil;
import com.tekcapsule.user.application.config.AppConstants;
import com.tekcapsule.user.application.function.input.AddBookmarkInput;
import com.tekcapsule.user.application.function.input.CreateInput;
import com.tekcapsule.user.application.mapper.InputOutputMapper;
import com.tekcapsule.user.domain.command.AddBookmarkCommand;
import com.tekcapsule.user.domain.command.CreateCommand;
import com.tekcapsule.user.domain.model.User;
import com.tekcapsule.user.domain.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
@Slf4j
public class BookmarkFunction implements Function<Message<AddBookmarkInput>, Message<User>> {

    private final UserService userService;

    public BookmarkFunction(final UserService userService) {
        this.userService = userService;
    }

    @Override
    public Message<User> apply(Message<AddBookmarkInput> addBookmarkInputMessage) {

        AddBookmarkInput addBookmarkInput = addBookmarkInputMessage.getPayload();

        log.info(String.format("Entering add bookmark Function - User Id:%S, Capsule Id:%S", addBookmarkInput.getUserId(),addBookmarkInput.getCapsuleId()));

        Origin origin = HeaderUtil.buildOriginFromHeaders(addBookmarkInputMessage.getHeaders());

        AddBookmarkCommand addBookmarkCommand = InputOutputMapper.buildBookmarkCommandFromBookmarkInput.apply(addBookmarkInput, origin);
        userService.addBookmark(addBookmarkCommand);
        Map<String, Object> responseHeader = new HashMap();
        responseHeader.put(AppConstants.HTTP_STATUS_CODE_HEADER, HttpStatus.OK.value());

        return new GenericMessage(responseHeader);
    }
}