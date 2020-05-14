package be.rommens.hera.autoconfigure;

import be.rommens.hera.api.service.ScraperFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * User : cederik
 * Date : 05/04/2020
 * Time : 09:43
 */
@SpringBootTest(classes = MockScraperAutoConfiguration.class)
@AutoConfigureScraperMock
public class ScraperAutoConfigurationTest {

    @Autowired
    private ScraperFactory scraperFactory;

    @Test
    public void whenSpringContextIsBootstrapped_thenNoExceptions() {
        assertThat(scraperFactory).isNotNull();

    }
}
