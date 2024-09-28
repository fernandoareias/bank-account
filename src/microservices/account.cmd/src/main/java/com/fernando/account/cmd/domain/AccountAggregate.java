package com.fernando.account.cmd.domain;

import com.fernando.account.cmd.api.commands.OpenAccountCommand;
import domain.AggregateRoot;
import events.AccountClosedEvent;
import events.AccountOpenedEvent;
import events.FundsDepositedEvent;
import events.FundsWithdrawEvent;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
public class AccountAggregate extends AggregateRoot {
    private Boolean active;
    private double balance;

    public double getBalance() {
        return balance;
    }

    public AccountAggregate(OpenAccountCommand command)
    {
        raiseEvent(AccountOpenedEvent.builder()
                .id(command.getId())
                .accountHolder(command.getAccountNumber())
                .createdAt(new Date())
                .accountType(command.getAccountType())
                .openingBalance(command.getOpeningBalance())
                .build()
        );
    }

    public void apply(AccountOpenedEvent event)
    {
        this.id = event.getId();
        this.active = true;
        this.balance = event.getOpeningBalance();
    }

    public void depositFunds(double amount)
    {
        if(!active)
            throw new IllegalStateException("Funds cannot be deposited into a closed account!");

        if(amount < 0)
            throw new IllegalStateException("The deposit amount must be greater than 0!");


        raiseEvent(FundsDepositedEvent.builder()
                .id(this.id)
                .amount(amount)
                .build());
    }

    public void apply(FundsDepositedEvent event)
    {
        this.id = event.getId();
        this.balance += event.getAmount();
    }

    public void withDrawFunds(double amount)
    {
        if(!active)
            throw new IllegalStateException("Funds cannot be deposited into a closed account!");

         raiseEvent(FundsWithdrawEvent.builder()
                 .id(this.id)
                 .amount(amount)
                 .build());
    }

    public void apply(FundsWithdrawEvent event)
    {
        this.id = event.getId();
        this.balance += event.getAmount();
    }

    public void closeAccount()
    {
        if(!active)
            throw new IllegalStateException("Funds cannot be deposited into a closed account!");

        raiseEvent(AccountClosedEvent.builder()
                .id(this.id)
                .build());
    }

    public void apply(AccountClosedEvent event)
    {
        this.id = event.getId();
        this.active = false;
    }
}
