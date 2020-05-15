package be.rommens.hera.providers.example;

import be.rommens.hera.api.models.ScrapedComic;
import be.rommens.hera.api.models.ScrapedIssue;
import be.rommens.hera.core.AbstractScraper;
import be.rommens.hera.core.ScrapingConfig;
import be.rommens.hera.core.ScrapingConfigParams;
import lombok.extern.slf4j.Slf4j;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * User : cederik
 * Date : 04/04/2020
 * Time : 13:57
 */
@Slf4j
public class ExampleScraper extends AbstractScraper {

    public ExampleScraper(ScrapingConfig scrapingConfig) {
        super(scrapingConfig);
    }

    @Override
    protected String buildUrlForComic(String technicalComicName) {
        return scrapingConfig.getProperty(ScrapingConfigParams.BASE_URL)  + "/comic/" + technicalComicName;
    }

    @Override
    protected String buildUrlForIssue(String technicalComicName, String issue) {
        return scrapingConfig.getProperty(ScrapingConfigParams.BASE_URL)  + "/comic" + technicalComicName + "/issue/" + issue;
    }

    @Override
    public ScrapedComic scrapeComic(String technicalComicName) throws IOException {
        ScrapedComic scrapedComic = new ScrapedComic();
        scrapedComic.setTitle(scrapingConfig.getProperty(ScrapingConfigParams.BASE_URL) );
        return scrapedComic;
    }

    @Override
    public ScrapedIssue scrapeIssue(String technicalComicName, String issue) throws IOException {
        return null;
    }

    @Override
    public byte[] downloadPage(String url) throws FileNotFoundException {
        return new byte[0];
    }
}
