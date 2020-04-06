package be.rommens.hera;

import be.rommens.hera.api.Publisher;
import be.rommens.hera.api.exceptions.ComicNotFoundException;
import be.rommens.hera.api.models.ScrapedComic;
import be.rommens.hera.api.models.ScrapedIssue;
import be.rommens.hera.builders.ScrapedComicBuilder;
import be.rommens.hera.builders.ScrapedIssueBuilder;
import be.rommens.hera.builders.ScrapedIssueDetailsBuilder;
import be.rommens.hera.core.Scraper;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * User : cederik
 * Date : 06/04/2020
 * Time : 14:26
 */
public class ScraperTestFactoryTest {

    @Test
    public void testWillReturnScrapedComic() throws IOException {
        ScrapedComic expected = new ScrapedComicBuilder()
            .title("title")
            .author("author")
            .publisher(Publisher.DC_COMICS)
            .cover("cover")
            .dateOfRelease("dateOfRelease")
            .status("status")
            .summary("summery")
            .addIssue(new ScrapedIssueDetailsBuilder().issue("issue").date("date").url("url"))
            .build();
        ScraperMock scraperMock = new ScraperMock(null);
        scraperMock.setExpectedScrapedComic(expected);

        Scraper scraper = ScraperTestFactory.willReturnScrapedComic(expected);
        ScrapedComic result = scraper.scrapeComic("name");

        assertThat(result.getTitle(), is(expected.getTitle()));
        assertThat(result.getAuthor(), is(expected.getAuthor()));
        assertThat(result.getPublisher(), is(expected.getPublisher()));
        assertThat(result.getCover(), is(expected.getCover()));
        assertThat(result.getDateOfRelease(), is(expected.getDateOfRelease()));
        assertThat(result.getStatus(), is(expected.getStatus()));
        assertThat(result.getSummary(), is(expected.getSummary()));
        assertThat(result.getIssues(), is(expected.getIssues()));
    }

    @Test
    public void testWillReturnScrapedIssue() throws IOException {
        ScrapedIssue expected = new ScrapedIssueBuilder()
            .comic("comic")
            .issueNumber("issueNumber")
            .numberOfPages(0)
            .addPage("page1")
            .addPage("page2")
            .build();

        ScraperMock scraperMock = new ScraperMock(null);
        scraperMock.setExpectedScrapedIssue(expected);

        Scraper scraper = ScraperTestFactory.willReturnScrapedIssue(expected);
        ScrapedIssue result = scraper.scrapeIssue("name", "issue");

        assertThat(result.getComic(), is(expected.getComic()));
        assertThat(result.getIssueNumber(), is(expected.getIssueNumber()));
        assertThat(result.getNumberOfPages(), is(expected.getNumberOfPages()));
        assertThat(result.getPages(), is(expected.getPages()));

    }

    @Test
    public void testWillThrowComicNotFoundFromComic() {
        ScraperMock scraperMock = new ScraperMock(null);
        scraperMock.setExpectedException("name");

        Scraper scraper = ScraperTestFactory.willThrowComicNotFound("name");
        assertThrows(ComicNotFoundException.class, () -> scraper.scrapeComic("unknown"));
    }

    @Test
    public void testWillThrowComicNotFoundFromIssue() {
        ScraperMock scraperMock = new ScraperMock(null);
        scraperMock.setExpectedException("name");

        Scraper scraper = ScraperTestFactory.willThrowComicNotFound("name");
        assertThrows(ComicNotFoundException.class, () -> scraper.scrapeIssue("unknown", "issue"));
    }
}
