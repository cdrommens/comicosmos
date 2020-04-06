package be.rommens.hera.autoconfigure;

import be.rommens.hera.api.Provider;
import be.rommens.hera.api.service.ScraperFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

/**
 * User : cederik
 * Date : 05/04/2020
 * Time : 09:43
 */
@SpringJUnitConfig(classes = ScraperAutoConfiguration.class)
public class ScraperAutoConfigurationTest {

    @Autowired
    private ScraperFactory scraperFactory;

    @Test
    public void whenSpringContextIsBootstrapped_thenNoExceptions() {
        assertThat(scraperFactory, is(notNullValue()));
        assertThat(scraperFactory.createScraper(Provider.READCOMICS), is(notNullValue()));
        assertThat(scraperFactory.createScraper(Provider.EXAMPLE), is(notNullValue()));

    }
}
