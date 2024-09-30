package com.fernando.account.query.infrastructure;

import domain.BaseEntity;
import infrastructure.QueryDispatcher;
import org.springframework.stereotype.Service;
import queries.Query;
import queries.QueryHandlerMethod;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class AccountQueryDispatcher implements QueryDispatcher {

    private final Map<Class<? extends Query>, List<QueryHandlerMethod>> routes = new HashMap<>();


    @Override
    public <T extends Query> void registerHandler(Class<T> type, QueryHandlerMethod<T> handler) {
        var handlers = routes.computeIfAbsent(type, c -> new LinkedList<>());
        handlers.add(handler);
    }

    @Override
    public <U extends BaseEntity> List<U> send(Query query) {
        var handler = routes.get(query.getClass());

        if(handler == null || handler.size() <= 0)
            throw new RuntimeException("No query handler was registered!");

        if(handler.size() > 1)
            throw new RuntimeException("Cannot send query to more than on handler!");

        return handler.get(0).handle(query);
    }
}
