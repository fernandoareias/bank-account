package com.fernando.account.cmd.api.commands;

import commands.Command;
import lombok.Data;

@Data
public class WithdrawFundsCommand extends Command {
    private double amount;
}
