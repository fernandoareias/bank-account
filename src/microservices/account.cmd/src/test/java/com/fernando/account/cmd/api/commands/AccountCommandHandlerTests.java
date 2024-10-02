package com.fernando.account.cmd.api.commands;

import com.fernando.account.cmd.api.commands.*;
import com.fernando.account.cmd.domain.AccountAggregate;
import handlers.EventSourcingHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class AccountCommandHandlerTest {

    @Mock
    private EventSourcingHandler<AccountAggregate> eventSourcingHandler;

    @InjectMocks
    private AccountCommandHandler accountCommandHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testHandle_OpenAccountCommand() {
        
        OpenAccountCommand command = mock(OpenAccountCommand.class);

        
        accountCommandHandler.handle(command);

        
        verify(eventSourcingHandler, times(1)).save(any(AccountAggregate.class));
    }

    @Test
    void testHandle_DepositFundsCommand() {
        
        DepositFundsCommand command = mock(DepositFundsCommand.class);
        AccountAggregate aggregate = mock(AccountAggregate.class);
        when(command.getId()).thenReturn("accountId");
        when(command.getAmount()).thenReturn(100.0);
        when(eventSourcingHandler.getById(command.getId())).thenReturn(aggregate);

        
        accountCommandHandler.handle(command);

        
        verify(aggregate, times(1)).depositFunds(100.0);
        verify(eventSourcingHandler, times(1)).save(aggregate);
    }

    @Test
    void testHandle_WithdrawFundsCommand_InsufficientFunds() {
        
        WithdrawFundsCommand command = mock(WithdrawFundsCommand.class);
        AccountAggregate aggregate = mock(AccountAggregate.class);
        when(command.getId()).thenReturn("accountId");
        when(command.getAmount()).thenReturn(200.0);
        when(eventSourcingHandler.getById(command.getId())).thenReturn(aggregate);
        when(aggregate.getBalance()).thenReturn(100.0);

        
        assertThrows(IllegalStateException.class, () -> accountCommandHandler.handle(command));

        // Verifying no save call due to exception
        verify(eventSourcingHandler, never()).save(aggregate);
    }

    @Test
    void testHandle_WithdrawFundsCommand_SufficientFunds() {
        
        WithdrawFundsCommand command = mock(WithdrawFundsCommand.class);
        AccountAggregate aggregate = mock(AccountAggregate.class);
        when(command.getId()).thenReturn("accountId");
        when(command.getAmount()).thenReturn(100.0);
        when(eventSourcingHandler.getById(command.getId())).thenReturn(aggregate);
        when(aggregate.getBalance()).thenReturn(200.0);

        
        accountCommandHandler.handle(command);

        
        verify(aggregate, times(1)).withDrawFunds(100.0);
        verify(eventSourcingHandler, times(1)).save(aggregate);
    }

    @Test
    void testHandle_CloseAccountCommand() {
        
        CloseAccountCommand command = mock(CloseAccountCommand.class);
        AccountAggregate aggregate = mock(AccountAggregate.class);
        when(command.getId()).thenReturn("accountId");
        when(eventSourcingHandler.getById(command.getId())).thenReturn(aggregate);

        
        accountCommandHandler.handle(command);

        
        verify(aggregate, times(1)).closeAccount();
        verify(eventSourcingHandler, times(1)).save(aggregate);
    }

    @Test
    void testHandle_RestoreDbCommand() {
        
        RestoreDbCommand command = mock(RestoreDbCommand.class);

        
        accountCommandHandler.handle(command);

        
        verify(eventSourcingHandler, times(1)).republisherEvents();
    }
}
