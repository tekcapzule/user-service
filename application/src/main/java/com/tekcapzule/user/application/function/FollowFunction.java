package com.tekcapzule.user.application.function;

import com.tekcapzule.core.domain.Origin;
import com.tekcapzule.core.utils.HeaderUtil;
import com.tekcapzule.core.utils.Outcome;
import com.tekcapzule.core.utils.PayloadUtil;
import com.tekcapzule.core.utils.Stage;
import com.tekcapzule.user.application.config.AppConfig;
import com.tekcapzule.user.application.function.input.FollowTopicInput;
import com.tekcapzule.user.application.mapper.InputOutputMapper;
import com.tekcapzule.user.domain.command.FollowTopicCommand;
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
public class FollowFunction implements Function<Message<FollowTopicInput>, Message<Void>> {

    private final UserService userService;

    private final AppConfig appConfig;

    public FollowFunction(final UserService userService, final AppConfig appConfig) {
        this.userService = userService;
        this.appConfig = appConfig;
    }

    @Override
    public Message<Void> apply(Message<FollowTopicInput> followTopicInputMessage) {
        Map<String, Object> responseHeaders = new HashMap<>();
        Map<String, Object> payload = new HashMap<>();
        String stage = appConfig.getStage().toUpperCase();
        try {
            FollowTopicInput followTopicInput = followTopicInputMessage.getPayload();
            log.info(String.format("Entering follow topic Function - User Id:%s, Topic Id:%s", followTopicInput.getUserId(), followTopicInput.getTopicCodes()));
            Origin origin = HeaderUtil.buildOriginFromHeaders(followTopicInputMessage.getHeaders());
            FollowTopicCommand followTopicCommand = InputOutputMapper.buildFollowTopicCommandFromFollowTopicInput.apply(followTopicInput, origin);
            userService.followTopic(followTopicCommand);
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