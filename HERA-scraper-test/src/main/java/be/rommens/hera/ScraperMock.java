package be.rommens.hera;

import be.rommens.hera.api.exceptions.ComicNotFoundException;
import be.rommens.hera.api.models.ScrapedComic;
import be.rommens.hera.api.models.ScrapedIssue;
import be.rommens.hera.core.AbstractScraper;
import be.rommens.hera.core.ScrapingConfig;

import java.io.IOException;

/**
 * User : cederik
 * Date : 06/04/2020
 * Time : 08:51
 */
public class ScraperMock extends AbstractScraper {

    private ScrapedComic scrapedComic;
    private ScrapedIssue scrapedIssue;
    private ComicNotFoundException comicNotFoundException;

    public ScraperMock(ScrapingConfig scrapingConfig) {
        super(scrapingConfig);
    }

    @Override
    protected String buildUrlForComic(String technicalComicName) {
        return null;
    }

    @Override
    protected String buildUrlForIssue(String technicalComicName, String issue) {
        return null;
    }

    @Override
    public ScrapedComic scrapeComic(String technicalComicName) throws IOException {
        if (scrapedComic != null) {
            return scrapedComic;
        }
        if (comicNotFoundException != null) {
            throw comicNotFoundException;
        }
        return null;
    }

    @Override
    public ScrapedIssue scrapeIssue(String technicalComicName, String issue) throws IOException {
        if (scrapedIssue != null) {
            return scrapedIssue;
        }
        if (comicNotFoundException != null) {
            throw comicNotFoundException;
        }
        return null;
    }

    public void setExpectedScrapedComic(ScrapedComic scrapedComic) {
        this.scrapedComic = scrapedComic;
    }

    public void setExpectedScrapedIssue(ScrapedIssue scrapedIssue) {
        this.scrapedIssue = scrapedIssue;
    }

    public void setExpectedException(String technicalName) {
        this.comicNotFoundException = new ComicNotFoundException(technicalName, null);
    }
}
