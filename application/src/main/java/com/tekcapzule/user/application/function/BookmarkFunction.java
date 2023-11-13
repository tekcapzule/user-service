package com.tekcapzule.user.application.function;

import com.tekcapzule.core.domain.Origin;
import com.tekcapzule.core.utils.HeaderUtil;
import com.tekcapzule.core.utils.Outcome;
import com.tekcapzule.core.utils.PayloadUtil;
import com.tekcapzule.core.utils.Stage;
import com.tekcapzule.user.application.config.AppConfig;
import com.tekcapzule.user.application.function.input.AddBookmarkInput;
import com.tekcapzule.user.application.mapper.InputOutputMapper;
import com.tekcapzule.user.domain.command.AddBookmarkCommand;
import com.tekcapzule.user.domain.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
@Slf4j
public class BookmarkFunction implements Function<Message<AddBookmarkInput>, Message<Void>> {

    private final UserService userService;

    private final AppConfig appConfig;

    public BookmarkFunction(final UserService userService, final AppConfig appConfig) {
        this.userService = userService;
        this.appConfig = appConfig;
    }

    @Override
    public Message<Void> apply(Message<AddBookmarkInput> addBookmarkInputMessage) {
        Map<String, Object> responseHeaders = new HashMap<>();
        Map<String, Object> payload = new HashMap<>();
        String stage = appConfig.getStage().toUpperCase();
        try {
            AddBookmarkInput addBookmarkInput = addBookmarkInputMessage.getPayload();
            log.info(String.format("Entering add bookmark Function - User Id:%s, Resource Id:%s", addBookmarkInput.getUserId(), addBookmarkInput.getBookmark().getResourceId()));
            Origin origin = HeaderUtil.buildOriginFromHeaders(addBookmarkInputMessage.getHeaders());
            AddBookmarkCommand addBookmarkCommand = InputOutputMapper.buildBookmarkCommandFromBookmarkInput.apply(addBookmarkInput, origin);
            userService.addBookmark(addBookmarkCommand);
            responseHeaders = HeaderUtil.populateResponseHeaders(responseHeaders, Stage.valueOf(stage), Outcome.SUCCESS);
            payload = PayloadUtil.composePayload(Outcome.SUCCESS);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            responseHeaders = HeaderUtil.populateResponseHeaders(responseHeaders, Stage.valueOf(stage), Outcome.ERROR);
            payload = PayloadUtil.composePayload(Outcome.ERROR);
        }
        return new GenericMessage(payload, responseHeaders);
    }
}