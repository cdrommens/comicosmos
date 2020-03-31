package be.rommens.hera;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.Map;

/**
 * User : cederik
 * Date : 31/03/2020
 * Time : 14:23
 */
@Configuration
@ConfigurationProperties(prefix = "providers")
@PropertySource("classpath:providers.properties")
public class ProviderProperty {

    private Map<String, String> url;

    public Map<String, String> getUrl() {
        return url;
    }

    public void setUrl(Map<String, String> url) {
        this.url = url;
    }
}
