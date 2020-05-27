package be.rommens.hermes.service;

import be.rommens.hermes.model.event.DomainEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

/**
 * User : cederik
 * Date : 02/05/2020
 * Time : 14:05
 */
@Component
@RequiredArgsConstructor
public class KafkaDomainEventPublisher implements DomainEventPublisher {

    private final Source source;

    @Override
    public void publish(DomainEvent domainEvent) {
        Message<?> message = MessageBuilder
            .withPayload(domainEvent)
            .setHeader("type", domainEvent.getType())
            .build();
        source.output().send(message);
    }
}
