package com.fernando.account.query.api.dtos;

import com.fernando.account.query.domain.BankAccount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import shared.BaseResponse;

import java.util.LinkedList;
import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class AccountLookupResponse extends BaseResponse {
    List<BankAccount> accounts;

    public AccountLookupResponse(String msg)
    {
        super(msg);
    }
}
