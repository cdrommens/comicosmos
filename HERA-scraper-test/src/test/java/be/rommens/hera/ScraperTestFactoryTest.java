package be.rommens.hera;

import be.rommens.hera.api.exceptions.ComicNotFoundException;
import be.rommens.hera.api.models.ScrapedComic;
import be.rommens.hera.api.models.ScrapedIssue;
import be.rommens.hera.builders.ScrapedComicBuilder;
import be.rommens.hera.builders.ScrapedIssueBuilder;
import be.rommens.hera.builders.ScrapedIssueDetailsBuilder;
import be.rommens.hera.core.Scraper;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.*;

/**
 * User : cederik
 * Date : 06/04/2020
 * Time : 14:26
 */
public class ScraperTestFactoryTest {

    @Test
    public void testWillReturnScrapedComic() throws IOException {
        //given
        ScrapedComic expected = new ScrapedComicBuilder()
            .title("title")
            .author("author")
            .publisher("DC Comics")
            .cover("cover")
            .dateOfRelease("dateOfRelease")
            .status("status")
            .summary("summery")
            .addIssue(new ScrapedIssueDetailsBuilder().issue("issue").date("date").url("url"))
            .build();
        ScraperMock scraperMock = new ScraperMock(null);
        scraperMock.setExpectedScrapedComic(expected);

        Scraper scraper = ScraperTestFactory.willReturnScrapedComic(expected);

        //when
        ScrapedComic result = scraper.scrapeComic("name");

        //then
        assertThat(result).isEqualToComparingFieldByField(expected);
    }

    @Test
    public void testWillReturnScrapedIssue() throws IOException {
        //given
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

        //when
        ScrapedIssue result = scraper.scrapeIssue("name", "issue");

        //then
        assertThat(result).isEqualToComparingFieldByField(expected);
    }

    @Test
    public void testWillThrowComicNotFoundFromComic() {
        //given
        ScraperMock scraperMock = new ScraperMock(null);
        scraperMock.setExpectedException("name");

        Scraper scraper = ScraperTestFactory.willThrowComicNotFound("name");

        //then
        assertThatThrownBy(() -> scraper.scrapeComic("unknown"))
            .isInstanceOf(ComicNotFoundException.class);
    }

    @Test
    public void testWillThrowComicNotFoundFromIssue() {
        //given
        ScraperMock scraperMock = new ScraperMock(null);
        scraperMock.setExpectedException("name");

        Scraper scraper = ScraperTestFactory.willThrowComicNotFound("name");

        //then
        assertThatThrownBy(() -> scraper.scrapeIssue("unknown", "issue"))
            .isInstanceOf(ComicNotFoundException.class);
    }

    @Test
    public void testWillThrowUnsupportedOperationException() {
        //given
        ScraperMock scraperMock = new ScraperMock(null);

        //then
        assertThatThrownBy(() -> scraperMock.buildUrlForComic("unknown"))
            .isInstanceOf(UnsupportedOperationException.class);
        assertThatThrownBy(() -> scraperMock.buildUrlForIssue("unknown", "issue"))
            .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    public void testWillThrowIllegalStateException() {
        //given
        Scraper scraper = ScraperTestFactory.willReturnScrapedComic(null);

        //when
        assertThatIllegalStateException().isThrownBy(() -> scraper.scrapeComic("name"));
        assertThatIllegalStateException().isThrownBy(() -> scraper.scrapeIssue("unknown", "issue"));
    }
}
