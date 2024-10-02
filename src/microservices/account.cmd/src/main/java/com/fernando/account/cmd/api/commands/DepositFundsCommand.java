package com.fernando.account.cmd.api.commands;

import commands.Command;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class DepositFundsCommand extends Command {

    protected DepositFundsCommand() {
    }


    public DepositFundsCommand(double amount) {

        if(amount < 0)
            throw new IllegalArgumentException("Amount invalid.");

        this.amount = amount;
    }

    private double amount;

    public double getAmount() {
        return amount;
    }
}
