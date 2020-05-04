package be.rommens.hera.api.service;

import be.rommens.hera.api.Provider;
import be.rommens.hera.core.Scraper;
import be.rommens.hera.providers.example.ExampleScraper;
import be.rommens.hera.providers.readcomics.ReadComicsScraper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

/**
 * User : cederik
 * Date : 04/04/2020
 * Time : 14:03
 */
@RequiredArgsConstructor
@Service
public class ScraperFactory {

    private final ApplicationContext applicationContext;

    public Scraper createScraper(Provider provider) {
        if (provider == null) {
            throw new IllegalStateException("Provider must not be null");
        }
        switch (provider) {
            case READCOMICS:
                return applicationContext.getBean(ReadComicsScraper.class);
            case EXAMPLE:
                return applicationContext.getBean(ExampleScraper.class);
            default:
                throw new IllegalStateException("No Scraper found for provider " + provider.name());
        }
    }
}
