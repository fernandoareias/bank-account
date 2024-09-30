package com.fernando.account.cmd.infrastructure;

import com.fernando.account.cmd.domain.AccountAggregate;
import domain.AggregateRoot;
import handlers.EventSourcingHandler;
import infrastructure.EventStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import producers.EventProducer;

import java.util.Comparator;

@Service
public class AccountEventSourcingHandler implements EventSourcingHandler<AccountAggregate> {

    @Autowired
    private EventStore eventStore;

    @Autowired
    private EventProducer eventProducer;

    @Override
    public void save(AggregateRoot aggregateRoot) {
        eventStore.saveEvents(aggregateRoot.getId(), aggregateRoot.getUncommittedChanges(), aggregateRoot.getVersion());
        aggregateRoot.markChangesAsCommitted();
    }

    @Override
    public AccountAggregate getById(String id) {
        var aggregate = new AccountAggregate();
        var events = eventStore.getEvents(id);
        if(events != null && !events.isEmpty())
        {
            aggregate.replayEvents(events);
            var latestVersion = events.stream().map(x -> x.getVersion()).max(Comparator.naturalOrder());
            aggregate.setVersion(latestVersion.get());
        }

        return aggregate;
    }

    @Override
    public void republisherEvents() {
        var aggregateIds = eventStore.getAggregateIds();

        for(var aggregateId: aggregateIds)
        {
            var aggregate = getById(aggregateId);
            if(aggregate == null || !aggregate.getActive()) return;
            var events = eventStore.getEvents(aggregateId);

            for(var e : events)
            {
                eventProducer.producer(e.getClass().getSimpleName(), e);
            }
        }
    }
}
