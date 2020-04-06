package be.rommens.hera;

import be.rommens.hera.api.models.ScrapedComic;
import be.rommens.hera.api.models.ScrapedIssue;
import be.rommens.hera.core.Scraper;

/**
 * User : cederik
 * Date : 06/04/2020
 * Time : 08:29
 */
public class ScraperTestFactory {

    public static Scraper willReturnScrapedComic(ScrapedComic result) {
        ScraperMock scraperMock = new ScraperMock(null);
        scraperMock.setExpectedScrapedComic(result);
        return scraperMock;
    }

    public static Scraper willReturnScrapedIssue(ScrapedIssue result) {
        ScraperMock scraperMock = new ScraperMock(null);
        scraperMock.setExpectedScrapedIssue(result);
        return scraperMock;
    }

    public static Scraper willThrowComicNotFound(String technicalName) {
        ScraperMock scraperMock = new ScraperMock(null);
        scraperMock.setExpectedException(technicalName);
        return scraperMock;
    }
}
