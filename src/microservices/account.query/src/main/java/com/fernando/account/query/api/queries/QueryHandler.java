package com.fernando.account.query.api.queries;

import domain.BaseEntity;

import java.util.List;

public interface QueryHandler {
    List<BaseEntity> handle(FindAccountByHolderQuery query);
    List<BaseEntity> handle(FindAccountByIdQuery query);
    List<BaseEntity> handle(FindAccountWithBalanceQuery query);
    List<BaseEntity> handle(FindAllAccountsQuery query);
}
