package com.fernando.account.cmd.infrastructure;

import com.fernando.account.cmd.domain.AccountAggregate;
import com.fernando.account.cmd.domain.EventStoreRepository;
import events.Event;
import events.EventModel;
import exceptions.AggregateNotFoundException;
import exceptions.ConcurrencyException;
import infrastructure.EventStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import producers.EventProducer;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountEventStore implements EventStore {

    @Autowired
    private EventProducer eventProducer;

    @Autowired
    private EventStoreRepository eventStoreRepository;

    @Override
    public void saveEvents(String aggregateId, Iterable<Event> events, int expectedVersion) {

        var eventStream = eventStoreRepository.findByAggregateIdentifier(aggregateId);
        if(expectedVersion != -1 && eventStream.get(eventStream.size() - 1).getVersion() != expectedVersion)
            throw new ConcurrencyException();

        var version = expectedVersion;
        for(var e : events)
        {
            version++;
            e.setVersion(version);
            var eventModel = EventModel.builder()
                    .timeStamp(new Date())
                    .aggregateIdentifier(aggregateId)
                    .aggregateType(AccountAggregate.class.getTypeName())
                    .version(version)
                    .eventType(e.getClass().getTypeName())
                    .eventDate(e)
                    .build();

            var persistedEvent = eventStoreRepository.save(eventModel);

            if(!persistedEvent.getId().isEmpty())
            {
                eventProducer.producer(e.getClass().getSimpleName(), e);
            }
        }
    }


    @Override
    public List<Event> getEvents(String aggregateId) {

        var eventStream = eventStoreRepository.findByAggregateIdentifier(aggregateId);
        if(eventStream == null || eventStream.isEmpty())
                throw new AggregateNotFoundException("Incorrect account ID provided!");

        return eventStream.stream().map(x -> x.getEventDate()).collect(Collectors.toList());

    }

    @Override
    public List<String> getAggregateIds() {
        var eventStreams = eventStoreRepository.findAll();

        return eventStreams.stream().map(EventModel::getAggregateIdentifier).distinct().collect(Collectors.toList());
    }
}
