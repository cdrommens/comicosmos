package be.rommens.hera.providers.readcomics.mappers;

import be.rommens.hera.api.Publisher;
import org.apache.commons.lang3.StringUtils;

/**
 * User : cederik
 * Date : 03/04/2020
 * Time : 20:56
 */
public class PublisherMapper {

    public Publisher mapTo(String publisher) {
        if (StringUtils.isEmpty(publisher)) {
            return null;
        }
        String sanitizedPublisher = StringUtils.remove(publisher, StringUtils.SPACE).toUpperCase();
        switch (sanitizedPublisher) {
            case "DCCOMICS":
                return Publisher.DC_COMICS;
            case "MARVELCOMICS":
                return Publisher.MARVEL_COMICS;
            default:
                return null;
        }
    }
}
