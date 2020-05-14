package be.rommens.hera.autoconfigure;

import be.rommens.hera.api.Provider;
import be.rommens.hera.api.service.ScraperFactory;
import be.rommens.hera.api.service.ScraperFactoryImpl;
import be.rommens.hera.core.Scraper;
import be.rommens.hera.core.ScrapingConfig;
import be.rommens.hera.core.ScrapingConfigParams;
import be.rommens.hera.providers.example.ExampleScraper;
import be.rommens.hera.providers.readcomics.ReadComicsScraper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * User : cederik
 * Date : 04/04/2020
 * Time : 14:46
 */
@Configuration
@ConditionalOnClass(ScraperFactory.class)
@EnableConfigurationProperties(ProviderProperties.class)
@PropertySource("classpath:providers.properties")
public class ScraperAutoConfiguration {

    private final ApplicationContext applicationContext;
    private final ProviderProperties providerProperties;

    public ScraperAutoConfiguration(ApplicationContext applicationContext, ProviderProperties providerProperties) {
        this.applicationContext = applicationContext;
        this.providerProperties = providerProperties;
    }

    @Bean
    @ConditionalOnMissingBean(ReadComicsScraper.class)
    public Scraper readComicsScraper() {
        ScrapingConfig config = new ScrapingConfig();
        config.put(ScrapingConfigParams.BASE_URL, providerProperties.getUrl().get(Provider.READCOMICS.getPropertyName()));
        return new ReadComicsScraper(config);
    }

    @Bean
    @ConditionalOnMissingBean(ExampleScraper.class)
    public Scraper exampleScraper() {
        ScrapingConfig config = new ScrapingConfig();
        config.put(ScrapingConfigParams.BASE_URL, providerProperties.getUrl().get(Provider.EXAMPLE.getPropertyName()));
        return new ExampleScraper(config);
    }

    @Bean
    @ConditionalOnMissingBean
    public ScraperFactory scraperFactory() {
        return new ScraperFactoryImpl(applicationContext);
    }
}
