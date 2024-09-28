package com.fernando.account.cmd.api.commands;

import commands.Command;
import lombok.Data;
import shared.AccountType;

@Data
public class OpenAccountCommand extends Command {
    private String accountNumber;
    private AccountType accountType;
    private double openingBalance;
}
