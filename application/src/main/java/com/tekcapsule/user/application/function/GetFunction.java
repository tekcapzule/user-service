package com.tekcapsule.user.application.function;

import com.tekcapsule.core.utils.HeaderUtil;
import com.tekcapsule.core.utils.Outcome;
import com.tekcapsule.core.utils.Stage;
import com.tekcapsule.user.application.function.input.GetInput;
import com.tekcapsule.user.application.config.AppConfig;
import com.tekcapsule.user.domain.model.User;
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
public class GetFunction implements Function<Message<GetInput>, Message<User>> {

    private final UserService userService;

    private final AppConfig appConfig;

    public GetFunction(final UserService userService, final AppConfig appConfig) {
        this.userService = userService;
        this.appConfig = appConfig;
    }


    @Override
    public Message<User> apply(Message<GetInput> getInputMessage) {
        Map<String, Object> responseHeaders = new HashMap<>();
        Map<String, Object> payload = new HashMap<>();
        User user = new User();
        String stage = appConfig.getStage().toUpperCase();
        try {
            GetInput getInput = getInputMessage.getPayload();
            log.info(String.format("Entering get user Function -  User Id:%s", getInput.getUserId()));
            user = userService.get(getInput.getUserId());
            Map<String, Object> responseHeader = new HashMap();
            if (user == null) {
                responseHeaders = HeaderUtil.populateResponseHeaders(responseHeaders, Stage.valueOf(stage), Outcome.NOT_FOUND);
                user = User.builder().build();
            } else {
                responseHeaders = HeaderUtil.populateResponseHeaders(responseHeaders, Stage.valueOf(stage), Outcome.SUCCESS);
            }
        } catch (Exception ex) {
            log.error(ex.getMessage());
            responseHeaders = HeaderUtil.populateResponseHeaders(responseHeaders, Stage.valueOf(stage), Outcome.ERROR);
        }
        return new GenericMessage(user, responseHeaders);
    }
}