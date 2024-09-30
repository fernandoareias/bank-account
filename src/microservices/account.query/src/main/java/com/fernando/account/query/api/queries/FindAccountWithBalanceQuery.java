package com.fernando.account.query.api.queries;

import com.fernando.account.query.api.dtos.EqualityType;
import lombok.AllArgsConstructor;
import lombok.Data;
import queries.Query;

@Data
@AllArgsConstructor
public class FindAccountWithBalanceQuery extends Query {
    private EqualityType equalityType;
    private double balance;
}
