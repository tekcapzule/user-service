package com.tekcapsule.user.application.function;

import com.tekcapsule.core.domain.Origin;
import com.tekcapsule.core.utils.HeaderUtil;
import com.tekcapsule.core.utils.Outcome;
import com.tekcapsule.core.utils.PayloadUtil;
import com.tekcapsule.core.utils.Stage;
import com.tekcapsule.user.application.config.AppConfig;
import com.tekcapsule.user.application.function.input.AddBookmarkInput;
import com.tekcapsule.user.application.function.input.RemoveBookmarkInput;
import com.tekcapsule.user.application.mapper.InputOutputMapper;
import com.tekcapsule.user.domain.command.AddBookmarkCommand;
import com.tekcapsule.user.domain.command.RemoveBookmarkCommand;
import com.tekcapsule.user.domain.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
@Slf4j
public class RemoveBookmarkFunction implements Function<Message<RemoveBookmarkInput>, Message<Void>> {
    private final UserService userService;

    private final AppConfig appConfig;

    public RemoveBookmarkFunction(final UserService userService, final AppConfig appConfig) {
        this.userService = userService;
        this.appConfig = appConfig;
    }

    @Override
    public Message<Void> apply(Message<RemoveBookmarkInput> removeBookmarkInputMessage) {
        Map<String, Object> responseHeaders = new HashMap<>();
        Map<String, Object> payload = new HashMap<>();
        String stage = appConfig.getStage().toUpperCase();
        try {
            RemoveBookmarkInput removeBookmarkInput = removeBookmarkInputMessage.getPayload();
            log.info(String.format("Entering remove bookmark Function - User Id:%s, Resource Id:%s", removeBookmarkInput.getUserId(), removeBookmarkInput.getBookmark().getResourceId()));
            Origin origin = HeaderUtil.buildOriginFromHeaders(removeBookmarkInputMessage.getHeaders());
            RemoveBookmarkCommand removeBookmarkCommand = InputOutputMapper.buildRemoveBookmarkCommandFromRemoveBookmarkInput.apply(removeBookmarkInput, origin);
            userService.removeBookmark(removeBookmarkCommand);
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
