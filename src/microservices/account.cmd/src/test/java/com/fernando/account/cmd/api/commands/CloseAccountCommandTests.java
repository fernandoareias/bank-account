package com.fernando.account.cmd.api.commands;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CloseAccountCommandTests {

    @Test
    public void ShouldCreateCloseAccountCommand()
    {
        // System out test
        var sut = new CloseAccountCommand();

        assertThat(sut).isNotNull();
    }
}
