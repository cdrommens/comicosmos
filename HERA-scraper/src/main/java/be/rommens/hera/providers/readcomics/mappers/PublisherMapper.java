package be.rommens.hera.providers.readcomics.mappers;

import be.rommens.hera.core.Publisher;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.NonNull;

/**
 * User : cederik
 * Date : 03/04/2020
 * Time : 20:56
 */
public class PublisherMapper {

    public Publisher mapTo(@NonNull String publisher) {
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
