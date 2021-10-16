package com.tekcapsule.user.application.function;

import com.tekcapsule.core.domain.Origin;
import com.tekcapsule.core.utils.HeaderUtil;
import com.tekcapsule.user.application.function.input.CreateInput;
import com.tekcapsule.user.application.config.AppConstants;
import com.tekcapsule.user.application.mapper.InputOutputMapper;
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
public class CreateFunction implements Function<Message<CreateInput>, Message<User>> {

    private final UserService userService;

    public CreateFunction(final UserService userService) {
        this.userService = userService;
    }


    @Override
    public Message<User> apply(Message<CreateInput> createInputMessage) {

        CreateInput createInput = createInputMessage.getPayload();

        log.info(String.format("Entering create user Function - User Id:{0}", createInput.getEmailId()));

        Origin origin = HeaderUtil.buildOriginFromHeaders(createInputMessage.getHeaders());

        CreateCommand createCommand = InputOutputMapper.buildCreateCommandFromCreateInput.apply(createInput, origin);
        User user = userService.create(createCommand);
        Map<String, Object> responseHeader = new HashMap();
        responseHeader.put(AppConstants.HTTP_STATUS_CODE_HEADER, HttpStatus.OK.value());

        return new GenericMessage(user, responseHeader);
    }
}