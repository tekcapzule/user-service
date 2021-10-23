package com.tekcapsule.user.application.function;

import com.tekcapsule.core.domain.Origin;
import com.tekcapsule.core.utils.HeaderUtil;
import com.tekcapsule.user.application.function.input.DisableInput;
import com.tekcapsule.user.application.mapper.InputOutputMapper;
import com.tekcapsule.user.domain.command.DisableCommand;
import com.tekcapsule.user.domain.service.UserService;
import com.tekcapsule.user.application.config.AppConstants;
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
public class DisableFunction implements Function<Message<DisableInput>, Message<Void>> {

    private final UserService userService;

    public DisableFunction(final UserService userService) {
        this.userService = userService;
    }


    @Override
    public Message<Void> apply(Message<DisableInput> disableInputMessage) {

        DisableInput disableInput = disableInputMessage.getPayload();

        log.info(String.format("Entering disable user Function - User Id:%s", disableInput.getUserId()));

        Origin origin = HeaderUtil.buildOriginFromHeaders(disableInputMessage.getHeaders());

        DisableCommand disableCommand = InputOutputMapper.buildDisableCommandFromDisableInput.apply(disableInput, origin);
        userService.disable(disableCommand);
        Map<String, Object> responseHeader = new HashMap();
        responseHeader.put(AppConstants.HTTP_STATUS_CODE_HEADER, HttpStatus.OK.value());
        return new GenericMessage( responseHeader);
    }
}
