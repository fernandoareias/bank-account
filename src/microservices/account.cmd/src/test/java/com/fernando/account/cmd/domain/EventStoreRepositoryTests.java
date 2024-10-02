package com.fernando.account.cmd.domain;

import events.AccountClosedEvent;
import events.EventModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
public class EventStoreRepositoryTests {

    @Autowired
    private EventStoreRepository eventStoreRepository;

    @Test
    public void shouldSaveEvent() {
        var e = new AccountClosedEvent();

        var aggregate = new AccountAggregate();


        var eventModel = EventModel.builder()
                .timeStamp(new Date())
                .aggregateIdentifier(aggregate.getId())
                .aggregateType(AccountAggregate.class.getTypeName())
                .version(1)
                .eventType(e.getClass().getTypeName())
                .eventDate(e)
                .build();

        var event = eventStoreRepository.save(eventModel);

        assertThat(event).isNotNull();
        assertThat(event.getAggregateIdentifier()).isEqualTo(aggregate.getId());
        assertThat(event.getEventType()).isEqualTo(e.getClass().getTypeName());
    }
}
