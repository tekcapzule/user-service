package com.tekcapsule.user.application.function;

import com.tekcapsule.user.application.function.input.GetInput;
import com.tekcapsule.user.application.config.AppConstants;
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
public class GetFunction implements Function<Message<GetInput>, Message<User>> {

    private final UserService userService;

    public GetFunction(final UserService userService) {
        this.userService = userService;
    }


    @Override
    public Message<User> apply(Message<GetInput> getInputMessage) {
        GetInput getInput = getInputMessage.getPayload();

        log.info(String.format("Entering get user Function -  User Id:{0}", getInput.getUserId()));

        User user = userService.get(getInput.getUserId());
        Map<String, Object> responseHeader = new HashMap();
        if (user == null) {
            responseHeader.put(AppConstants.HTTP_STATUS_CODE_HEADER, HttpStatus.NOT_FOUND.value());
            user = User.builder().build();
        } else {
            responseHeader.put(AppConstants.HTTP_STATUS_CODE_HEADER, HttpStatus.OK.value());
        }
        return new GenericMessage(user, responseHeader);
    }
}