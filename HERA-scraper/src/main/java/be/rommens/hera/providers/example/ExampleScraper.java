package be.rommens.hera.providers.example;

import be.rommens.hera.api.Provider;
import be.rommens.hera.api.models.ScrapedComic;
import be.rommens.hera.api.models.ScrapedIssue;
import be.rommens.hera.core.AbstractScraper;
import be.rommens.hera.core.ProviderProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * User : cederik
 * Date : 04/04/2020
 * Time : 13:57
 */
@Slf4j
@Component("exampleScraper")
public class ExampleScraper extends AbstractScraper {

    private final String base;

    public ExampleScraper(ProviderProperty providerProperty) {
        super(providerProperty);
        this.base = getProviderProperty(Provider.EXAMPLE);
    }

    @Override
    protected String buildUrlForComic(String technicalComicName) {
        return base + "/comic/" + technicalComicName;
    }

    @Override
    protected String buildUrlForIssue(String technicalComicName, String issue) {
        return base + "/comic" + technicalComicName + "/issue/" + issue;
    }

    @Override
    public ScrapedComic scrapeComic(String technicalComicName) throws IOException {
        ScrapedComic scrapedComic = new ScrapedComic();
        scrapedComic.setTitle(base);
        return scrapedComic;
    }

    @Override
    public ScrapedIssue scrapeIssue(String technicalComicName, String issue) throws IOException {
        return null;
    }
}
