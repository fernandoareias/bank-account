package com.fernando.account.query.api.queries;

import lombok.AllArgsConstructor;
import lombok.Data;
import queries.Query;

@Data
@AllArgsConstructor
public class FindAccountByHolderQuery extends Query  {
    private String accountHolder;
}
