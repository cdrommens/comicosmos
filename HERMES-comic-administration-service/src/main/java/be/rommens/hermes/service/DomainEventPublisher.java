package be.rommens.hermes.service;

import be.rommens.hermes.model.event.DomainEvent;

/**
 * User : cederik
 * Date : 02/05/2020
 * Time : 14:02
 */
public interface DomainEventPublisher {

    void publish(DomainEvent domainEvent);
}
