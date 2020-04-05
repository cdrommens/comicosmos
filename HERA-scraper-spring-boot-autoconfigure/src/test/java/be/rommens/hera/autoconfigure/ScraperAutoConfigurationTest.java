package be.rommens.hera.autoconfigure;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * User : cederik
 * Date : 05/04/2020
 * Time : 09:43
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ScraperAutoConfiguration.class)
public class ScraperAutoConfigurationTest {

    @Test
    public void whenSpringContextIsBootstrapped_thenNoExceptions() {
    }
}
