package com.fernando.account.cmd.api.commands;

import commands.Command;
import lombok.Data;
import shared.AccountType;

@Data
public class OpenAccountCommand extends Command {

    protected void OpenAccountCommand(){

    }

    public OpenAccountCommand(String accountNumber, AccountType accountType, double openingBalance) {

        if(accountNumber.isEmpty())
            throw new IllegalArgumentException("Account number invalid.");

        if(accountType == null)
            throw new IllegalArgumentException("Account type invalid.");

        if(openingBalance < 0)
            throw new IllegalArgumentException("Opening balance is invalid.");

        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.openingBalance = openingBalance;
    }

    private String accountNumber;
    private AccountType accountType;
    private double openingBalance;


    public String getAccountNumber() {
        return accountNumber;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public double getOpeningBalance() {
        return openingBalance;
    }
}
