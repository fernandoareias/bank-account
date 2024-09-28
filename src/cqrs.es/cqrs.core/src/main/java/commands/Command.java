package commands;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import messages.Message;

@Data
@AllArgsConstructor
@SuperBuilder
public abstract class Command extends Message {

}
