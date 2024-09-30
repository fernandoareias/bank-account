package queries;

import domain.BaseEntity;

import java.util.List;

@FunctionalInterface
public interface QueryHandlerMethod<T extends Query> {
    List<BaseEntity> handle(T query);
}
