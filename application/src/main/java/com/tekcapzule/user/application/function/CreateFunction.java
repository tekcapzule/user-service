package com.tekcapzule.user.application.function;

import com.amazonaws.services.lambda.runtime.events.CognitoUserPoolPostConfirmationEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tekcapzule.core.domain.Origin;
import com.tekcapzule.core.domain.SourceSystem;
import com.tekcapzule.core.utils.HeaderUtil;
import com.tekcapzule.core.utils.Outcome;
import com.tekcapzule.core.utils.PayloadUtil;
import com.tekcapzule.core.utils.Stage;
import com.tekcapzule.user.application.config.AppConfig;
import com.tekcapzule.user.application.function.input.CreateInput;
import com.tekcapzule.user.application.mapper.InputOutputMapper;
import com.tekcapzule.user.domain.command.CreateCommand;
import com.tekcapzule.user.domain.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
@Slf4j
public class CreateFunction  implements Function<CognitoUserPoolPostConfirmationEvent,CognitoUserPoolPostConfirmationEvent> {

    private final UserService userService;

    private final AppConfig appConfig;

    public CreateFunction(final UserService userService, final AppConfig appConfig) {
        this.userService = userService;
        this.appConfig = appConfig;
    }

    private Message<CreateInput> getCreateInputMessage(Map<String, String> userAttributes) {
        CreateInput payload = new CreateInput();
        payload.setEmailId(userAttributes.get("email"));
        payload.setFirstName(userAttributes.get("given_name"));
        payload.setLastName(userAttributes.get("family_name"));
        payload.setPhoneNumber(userAttributes.get("phone_number"));

        Message<CreateInput> message = new GenericMessage<>(payload);
        // Add headers to the message
        return MessageBuilder
                .fromMessage(message)
                .setHeader("x-channel-code", SourceSystem.WEB_CLIENT)
                .setHeader("x-user-login",payload.getEmailId())
                .build();
    }

    @Override
    public CognitoUserPoolPostConfirmationEvent apply(CognitoUserPoolPostConfirmationEvent cognitoUserPoolPostConfirmationEvent) {
        Map<String, Object> responseHeaders = new HashMap<>();
        Map<String, Object> payload = new HashMap<>();
        String stage = appConfig.getStage().toUpperCase();
        Message<CreateInput> createInputMessage = getCreateInputMessage(cognitoUserPoolPostConfirmationEvent.getRequest().getUserAttributes());
        try {
            CreateInput createInput = createInputMessage.getPayload();
            log.info(String.format("Entering create user Function - User Id:%s", createInput.getEmailId()));
            log.info(String.format("Entering create user Function - Phone No:%s", createInput.getPhoneNumber()));
            Origin origin = HeaderUtil.buildOriginFromHeaders(createInputMessage.getHeaders());
            CreateCommand createCommand = InputOutputMapper.buildCreateCommandFromCreateInput.apply(createInput, origin);
            userService.create(createCommand);
            responseHeaders = HeaderUtil.populateResponseHeaders(responseHeaders, Stage.valueOf(stage), Outcome.SUCCESS);
            payload = PayloadUtil.composePayload(Outcome.SUCCESS);

            log.info("Response headers - %s", getJsonString(responseHeaders));
            log.info("payload - %s", getJsonString(payload));

            return cognitoUserPoolPostConfirmationEvent;
        }  catch (Exception ex) {
            log.error(ex.getMessage());
            responseHeaders = HeaderUtil.populateResponseHeaders(responseHeaders, Stage.valueOf(stage), Outcome.ERROR);
            payload = PayloadUtil.composePayload(Outcome.ERROR);
        }
        return null;
    }

    private String getJsonString(Map<String, Object> responseHeaders) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        // Convert map to JSON string
        return objectMapper.writeValueAsString(responseHeaders);
    }
}