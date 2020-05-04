package be.rommens.zeus.model;

import be.rommens.zeus.model.entity.Publisher;

/**
 * User : cederik
 * Date : 17/04/2020
 * Time : 10:59
 */
public class PublisherTestObjectFactory {

    public static Publisher getDcComicsPublisher() {
        Publisher publisher = new Publisher();
        publisher.setPublisherId(1);
        publisher.setName("DC Comics");
        publisher.setKey("DCCOMICS");
        return publisher;
    }

    public static Publisher getMarvelComicsPublisher() {
        Publisher publisher = new Publisher();
        publisher.setPublisherId(2);
        publisher.setName("Marvel Comics");
        publisher.setKey("MARVELCOMICS");
        return publisher;
    }
}
