package com.tekcapsule.user.application.function;

import com.tekcapsule.user.application.function.input.SearchInput;
import com.tekcapsule.user.application.config.AppConstants;
import com.tekcapsule.user.domain.query.SearchItem;
import com.tekcapsule.user.domain.query.SearchQuery;
import com.tekcapsule.user.domain.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;


@Component
@Slf4j
public class SearchFunction implements Function<Message<SearchInput>, Message<List<SearchItem>>> {

    private final UserService userService;

    public SearchFunction(final UserService userService) {
        this.userService = userService;
    }


    @Override
    public Message<List<SearchItem>> apply(Message<SearchInput> searchInputMessage) {
        SearchInput searchInput = searchInputMessage.getPayload();

        log.info(String.format("Entering search user Function"));

        List<SearchItem> searchItems = userService.search(SearchQuery.builder().tenantId("").build());
        Map<String, Object> responseHeader = new HashMap();
        responseHeader.put(AppConstants.HTTP_STATUS_CODE_HEADER, HttpStatus.OK.value());

        return new GenericMessage(searchItems, responseHeader);
    }
}