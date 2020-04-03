package be.rommens.hera.providers;

import be.rommens.hera.core.Publisher;
import be.rommens.hera.core.exceptions.ComicNotFoundException;
import be.rommens.hera.core.models.ScrapedComic;
import be.rommens.hera.core.models.ScrapedIssue;
import be.rommens.hera.core.models.ScrapedIssueDetails;
import be.rommens.hera.providers.readcomics.ReadComicsScraper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.TestPropertySource;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * User : cederik
 * Date : 29/03/2020
 * Time : 14:22
 *
 * UseFull links
 * -------------
 * Wiremock recording : http://wiremock.org/docs/record-playback/
 */
@SpringBootTest(classes = ReadComicsScraper.class)
@AutoConfigureWireMock(port = 8888)
@TestPropertySource("classpath:providers-test.properties")  //TODO : use DynamicTestProperty for dynamic port of wiremock
public class ReadComicsScraperTest {

    @Autowired
    private ReadComicsScraper readComicsScraper;

    @Test
    public void test() {
        assertThat(readComicsScraper.getProviderProperty(), is("http://localhost:8888/readcomics/comic/"));
    }

    @Test
    public void testScrapComicFound() throws IOException {
        ScrapedComic scrapedComic = readComicsScraper.scrapeComic("batman-2016");
        assertThat(scrapedComic.getTitle(), is("Batman (2016-)"));
        assertThat(scrapedComic.getPublisher(), is(Publisher.DC_COMICS));
        assertThat(scrapedComic.getAuthor(), is("Tom King"));
        assertThat(scrapedComic.getDateOfRelease(), is("2016"));
        assertThat(scrapedComic.getStatus(), is("Ongoing"));
        assertThat(scrapedComic.getCover(), is("//readcomicsonline.ru/uploads/manga/batman-2016/cover/cover_250x350.jpg"));
        assertThat(scrapedComic.getSummary(), is("\"I AM GOTHAM\" Chapter One No one has ever stopped the Caped Crusader. Not The Joker. Not Two-Face. Not " +
            "even the entire Justice League. But how does Batman confront a new hero who wants to save the city from the Dark Knight? CAN'T MISS: Superstar " +
            "artist David Finch returns to Batman alongside writer Tom King for this five-part storyline."));
        assertThat(scrapedComic.getIssues(), hasSize(95));
        ScrapedIssueDetails expectedIssueRegular = new ScrapedIssueDetails("Batman (2016-) #33",
            "https://readcomicsonline.ru/comic/batman-2016/33", "18 Oct. 2017");
        ScrapedIssueDetails expectedIssueAnnual = new ScrapedIssueDetails("Batman (2016-) #Annual 3",
            "https://readcomicsonline.ru/comic/batman-2016/Annual-3", "12 Dec. 2018");
        assertThat(scrapedComic.getIssues(), hasItem(expectedIssueRegular));
        assertThat(scrapedComic.getIssues(), hasItem(expectedIssueAnnual));
    }

    @Test
    public void testScrapeComicNotFound() {
        Exception exception = assertThrows(ComicNotFoundException.class, () -> readComicsScraper.scrapeComic("unknown"));
        assertThat(exception.getMessage(), is("URL for unknown is not found"));
    }

    @Test
    public void testScrapeIssueFound() throws IOException {
        ScrapedIssue scrapedIssue = readComicsScraper.scrapeIssue("batman-2016", "1");
        assertThat(scrapedIssue.getComic(), is("batman-2016"));
        assertThat(scrapedIssue.getIssueNumber(), is("1"));
        assertThat(scrapedIssue.getNumberOfPages(), is(25));
        assertThat(scrapedIssue.getPages(), not(empty()));
        assertThat(scrapedIssue.getPages(), hasSize(25));
        assertThat(scrapedIssue.getPages(), hasItem("https://readcomicsonline.ru/uploads/manga/batman-2016/chapters/1/16.jpg"));
    }

    @Test
    public void testScrapeIssueNotFound() {
        Exception exception = assertThrows(ComicNotFoundException.class, () -> readComicsScraper.scrapeIssue("unknown", "999"));
        assertThat(exception.getMessage(), is("URL for unknown with issue 999 is not found"));
    }

}
