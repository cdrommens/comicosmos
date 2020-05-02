package be.rommens.zeus.service;

import be.rommens.zeus.model.DomainEvent;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * User : cederik
 * Date : 02/05/2020
 * Time : 14:05
 */
@Component
public class KafkaDomainEventPublisher implements DomainEventPublisher {

    private final Source source;

    public KafkaDomainEventPublisher(Source source) {
        this.source = source;
    }

    @Override
    public void publish(DomainEvent domainEvent) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("type", domainEvent.getType());
        source.output().send(new GenericMessage<>(domainEvent, headers));
    }
}
