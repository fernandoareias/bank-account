package events;


import shared.AccountType;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@SuperBuilder
public class AccountOpenedEvent extends Event {

    private String accountHolder;
    private AccountType accountType;
    private Date createdAt;
    private double openingBalance;
}
