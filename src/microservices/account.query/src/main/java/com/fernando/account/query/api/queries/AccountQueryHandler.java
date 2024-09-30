package com.fernando.account.query.api.queries;

import com.fernando.account.query.api.dtos.EqualityType;
import com.fernando.account.query.domain.AccountRepository;
import domain.BaseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import queries.Query;
import com.fernando.account.query.api.queries.QueryHandler;

import java.util.LinkedList;
import java.util.List;

@Service
public class AccountQueryHandler implements QueryHandler {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public List<BaseEntity> handle(FindAccountByHolderQuery query) {
        var bankAccount = accountRepository.findByAccountHolder(query.getAccountHolder());

        if(bankAccount.isEmpty()) return null;

        List<BaseEntity> bankAccountList = new LinkedList<>();
        bankAccountList.add(bankAccount.get());
        return bankAccountList;
    }

    @Override
    public List<BaseEntity> handle(FindAccountByIdQuery query) {
        var bankAccount = accountRepository.findById(query.getId());
        List<BaseEntity> bankAccountList = new LinkedList<>();
        bankAccountList.add(bankAccount.get());
        return bankAccountList;
    }

    @Override
    public List<BaseEntity> handle(FindAccountWithBalanceQuery query) {
        var bankAccounts = query.getEqualityType() == EqualityType.GREATER_THAN ?
                accountRepository.findByBalanceGreaterThan(query.getBalance())
                :
                accountRepository.findByBalanceLessThan(query.getBalance());

        return bankAccounts;
    }

    @Override
    public List<BaseEntity> handle(FindAllAccountsQuery query) {
        var bankAccounts = accountRepository.findAll();
        List<BaseEntity> bankAccountList = new LinkedList<>();
        bankAccounts.forEach(bankAccountList::add);
        return bankAccountList;
    }
}
