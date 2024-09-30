package infrastructure;

import events.Event;

import java.util.List;

public interface EventStore {
    void saveEvents(String aggregateId, Iterable<Event> events, int expectedVersion);
    List<Event> getEvents(String aggregateId);
    List<String> getAggregateIds();
}
