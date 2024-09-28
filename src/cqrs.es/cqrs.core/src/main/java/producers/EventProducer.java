package producers;

import events.Event;

public interface EventProducer {
    void producer(String topic, Event event);
}
