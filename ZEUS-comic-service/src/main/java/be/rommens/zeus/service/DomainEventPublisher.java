package be.rommens.zeus.service;

import be.rommens.zeus.model.DomainEvent;

/**
 * User : cederik
 * Date : 02/05/2020
 * Time : 14:02
 */
public interface DomainEventPublisher {

    void publish(DomainEvent domainEvent);
}
