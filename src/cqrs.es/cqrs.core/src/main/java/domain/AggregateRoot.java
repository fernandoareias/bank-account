package domain;

import events.Event;

import java.text.MessageFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class AggregateRoot {
    protected String id;
    private int version = -1;
    private final List<Event> changes = new LinkedList<>();
    private final Logger logger = Logger.getLogger(AggregateRoot.class.getName());


    public String getId() {
        return id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public List<Event> getUncommittedChanges(){
        return this.changes;
    }

    public void markChangesAsCommitted(){
        this.changes.clear();
    }

    protected void applyChange(Event event, Boolean isNewEvent)
    {
        try {
            var method = getClass().getDeclaredMethod("apply", event.getClass());
            method.setAccessible(true);
            method.invoke(this, event);
        } catch (NoSuchMethodException e) {
            logger.log(Level.WARNING, MessageFormat.format("The apply method was not found in the aggregate for {0}", event.getClass().getName()));
        } catch (Exception e)
        {
            logger.log(Level.SEVERE, "Error applying event to aggregate", e);
        }finally {
            if(isNewEvent)
                changes.add(event);
        }
    }

    public void raiseEvent(Event event)
    {
        applyChange(event, true);
    }

    public void replayEvents(Iterable<Event> events)
    {
        events.forEach(e -> applyChange(e, false));
    }
}
