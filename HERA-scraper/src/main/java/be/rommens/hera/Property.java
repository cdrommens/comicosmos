package be.rommens.hera;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * User : cederik
 * Date : 31/03/2020
 * Time : 14:23
 */
@Configuration
@ConfigurationProperties(prefix = "service")
@PropertySource("classpath:service.properties")
public class Property {

    /**
     * A message for the service.
     */
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
