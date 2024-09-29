package com.fernando.account.query.infrastructure.handlers;

import events.AccountClosedEvent;
import events.AccountOpenedEvent;
import events.FundsDepositedEvent;
import events.FundsWithdrawEvent;

public interface EventHandler {

    void on(AccountOpenedEvent event);
    void on(FundsDepositedEvent event);
    void on(FundsWithdrawEvent event);
    void on(AccountClosedEvent event);

}
