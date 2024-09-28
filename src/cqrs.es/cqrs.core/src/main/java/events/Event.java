package events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import messages.Message;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public abstract class Event extends Message {
    private int version;

}
