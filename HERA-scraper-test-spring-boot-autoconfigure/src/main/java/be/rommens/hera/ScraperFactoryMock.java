package be.rommens.hera;

import be.rommens.hera.api.Provider;
import be.rommens.hera.api.service.ScraperFactory;
import be.rommens.hera.core.Scraper;
import lombok.RequiredArgsConstructor;

/**
 * User : cederik
 * Date : 14/05/2020
 * Time : 10:03
 */
@RequiredArgsConstructor
public class ScraperFactoryMock implements ScraperFactory {

    private final Scraper scraper;

    @Override
    public Scraper createScraper(Provider provider) {
        return scraper;
    }
}
