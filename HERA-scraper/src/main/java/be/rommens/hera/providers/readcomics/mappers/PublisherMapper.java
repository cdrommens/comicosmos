package be.rommens.hera.providers.readcomics.mappers;

import be.rommens.hera.api.Publisher;
import be.rommens.hera.core.Mapper;

/**
 * User : cederik
 * Date : 03/04/2020
 * Time : 20:56
 */
public class PublisherMapper implements Mapper<Publisher> {

    @Override
    public Publisher mappingLogic(String valueInUppercase) {
        switch (valueInUppercase) {
            case "DCCOMICS":
                return Publisher.DC_COMICS;
            case "MARVELCOMICS":
                return Publisher.MARVEL_COMICS;
            default:
                return null;
        }
    }
}
