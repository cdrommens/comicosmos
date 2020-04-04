package be.rommens.hera.core;

import be.rommens.hera.api.Provider;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.io.IOException;

/**
 * User : cederik
 * Date : 04/04/2020
 * Time : 13:38
 */
@EnableConfigurationProperties(ProviderProperty.class)
public abstract class AbstractScraper implements Scraper{

    private final ProviderProperty providerProperty;

    public AbstractScraper(ProviderProperty providerProperty) {
        this.providerProperty = providerProperty;
    }

    protected String getProviderProperty(Provider provider) {
        if (provider != null) {
            return providerProperty.getUrl().get(provider.getPropertyName());
        }
        return null;
    }

    protected Document getSource(String url) throws IOException {
        return Jsoup.connect(url)
            .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
            .header("Accept-Language", "en-US,en;q=0.9,fr-FR;q=0.8,fr;q=0.7,la;q=0.6")
            .header("Connection", "keep-alive")
            .userAgent(RandomUserAgent.getRandomUserAgent())
            .header("Pragma", "no-cache")
            .header("Host", "google.com")
            .get();
    }

    protected abstract String buildUrlForComic(String technicalComicName);

    protected abstract String buildUrlForIssue(String technicalComicName, String issue);

}
