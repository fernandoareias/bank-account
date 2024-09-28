package com.fernando.account.cmd.infrastructure;

import events.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import producers.EventProducer;

@Service
public class AccountEventProducer implements EventProducer {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void producer(String topic, Event event) {
        this.kafkaTemplate.send(topic, event);
    }

}
