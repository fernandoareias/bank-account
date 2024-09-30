package infrastructure;

import domain.BaseEntity;
import queries.Query;
import queries.QueryHandlerMethod;

import java.util.List;

public interface QueryDispatcher {
    <T extends Query> void registerHandler(Class<T> type, QueryHandlerMethod<T> handler);
    <U extends BaseEntity> List<U> send(Query query);
}
