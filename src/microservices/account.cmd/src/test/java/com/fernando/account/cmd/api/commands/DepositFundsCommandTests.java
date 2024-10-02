package com.fernando.account.cmd.api.commands;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DepositFundsCommandTests {

    @Test
    public void ShouldCreateDepositFundsCommand(){
        var sut = new DepositFundsCommand(1000);

        assertThat(sut).isNotNull();
    }

    @Test
    public void ShouldCreateDepositFundsCommandWithAmount(){
        var sut = new DepositFundsCommand(1000);

        assertThat(sut.getAmount()).isEqualTo(1000);
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionWhenAmountIsNegative() {
        assertThrows(IllegalArgumentException.class, () -> new DepositFundsCommand(-1000));
    }

}
