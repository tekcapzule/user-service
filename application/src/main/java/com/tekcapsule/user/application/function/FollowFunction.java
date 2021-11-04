package com.tekcapsule.user.application.function;

import com.tekcapsule.core.domain.Origin;
import com.tekcapsule.core.utils.HeaderUtil;
import com.tekcapsule.user.application.config.AppConstants;
import com.tekcapsule.user.application.function.input.FollowTopicInput;
import com.tekcapsule.user.application.mapper.InputOutputMapper;
import com.tekcapsule.user.domain.command.FollowTopicCommand;
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
public class FollowFunction implements Function<Message<FollowTopicInput>, Message<Void>> {

    private final UserService userService;

    public FollowFunction(final UserService userService) {
        this.userService = userService;
    }

    @Override
    public Message<Void> apply(Message<FollowTopicInput> followTopicInputMessage) {

        FollowTopicInput followTopicInput = followTopicInputMessage.getPayload();

        log.info(String.format("Entering follow topic Function - User Id:%s, Topic Id:%s", followTopicInput.getUserId(),followTopicInput.getTopicCode()));

        Origin origin = HeaderUtil.buildOriginFromHeaders(followTopicInputMessage.getHeaders());

        FollowTopicCommand followTopicCommand = InputOutputMapper.buildFollowTopicCommandFromFollowTopicInput.apply(followTopicInput, origin);
        userService.followTopic(followTopicCommand);
        Map<String, Object> responseHeader = new HashMap();
        responseHeader.put(AppConstants.HTTP_STATUS_CODE_HEADER, HttpStatus.OK.value());

        return new GenericMessage(responseHeader);
    }
}