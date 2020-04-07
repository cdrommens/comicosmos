package be.rommens.hera.api.service;

import be.rommens.hera.api.Provider;
import be.rommens.hera.core.Scraper;
import be.rommens.hera.core.ScrapingConfig;
import be.rommens.hera.providers.example.ExampleScraper;
import be.rommens.hera.providers.readcomics.ReadComicsScraper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * User : cederik
 * Date : 07/04/2020
 * Time : 15:11
 */
@SpringBootTest(classes = {ScraperFactory.class, ScraperFactoryTest.TestConfig.class})
public class ScraperFactoryTest {

    @Autowired
    private ScraperFactory scraperFactory;

    @TestConfiguration
    static class TestConfig {
        @Autowired
        private Environment environment;

        @Bean
        ScrapingConfig config() {
            return new ScrapingConfig();
        }

        @Bean
        Scraper readComicsScraper() {
            return new ReadComicsScraper(config());
        }

        @Bean
        Scraper exampleScraper() {
            return new ExampleScraper(config());
        }
    }

    @Test
    public void testGetReadComicsScraper() {
        assertThat(scraperFactory.createScraper(Provider.READCOMICS), is(instanceOf(ReadComicsScraper.class)));
    }

    @Test
    public void testGetExampleScraper() {
        assertThat(scraperFactory.createScraper(Provider.EXAMPLE), is(instanceOf(ExampleScraper.class)));
    }

    @Test
    public void testWillThrowErrorWithNoProvider() {
        assertThrows(IllegalStateException.class, () -> scraperFactory.createScraper(null));
    }

}
