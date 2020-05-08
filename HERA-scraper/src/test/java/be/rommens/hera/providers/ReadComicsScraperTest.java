package be.rommens.hera.providers;

import be.rommens.hera.api.Provider;
import be.rommens.hera.api.exceptions.ComicNotFoundException;
import be.rommens.hera.api.models.ScrapedComic;
import be.rommens.hera.api.models.ScrapedIssue;
import be.rommens.hera.api.models.ScrapedIssueDetails;
import be.rommens.hera.core.Scraper;
import be.rommens.hera.core.ScrapingConfig;
import be.rommens.hera.core.ScrapingConfigParams;
import be.rommens.hera.providers.readcomics.ReadComicsScraper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

import java.io.IOException;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * User : cederik
 * Date : 29/03/2020
 * Time : 14:22
 *
 * UseFull links
 * -------------
 * Wiremock recording : http://wiremock.org/docs/record-playback/
 */
@SpringBootTest(
    classes = {ReadComicsScraper.class, ReadComicsScraperTest.TestConfig.class},
    properties = {
        "providers.url.readcomics=http://localhost:${wiremock.server.port}/readcomics/"
    }
)
@AutoConfigureWireMock(port = 0)
public class ReadComicsScraperTest {

    @Autowired
    private Scraper readComicsScraper;

    @TestConfiguration
    static class TestConfig {
        @Autowired
        private Environment environment;

        @Bean
        ScrapingConfig config() {
            ScrapingConfig config = new ScrapingConfig();
            config.setProperty(ScrapingConfigParams.BASE_URL,
                environment.getProperty(ScrapingConfigParams.BASE_URL + "." + Provider.READCOMICS.getPropertyName()));
            return config;
        }
    }

    @Test
    public void testScrapComicFound() throws IOException {
        //when
        ScrapedComic scrapedComic = readComicsScraper.scrapeComic("batman-2016");

        //then
        ScrapedComic expected = new ScrapedComic();
        expected.setTitle("Batman (2016-)");
        expected.setPublisher("DC Comics");
        expected.setAuthor("Tom King");
        expected.setDateOfRelease("2016");
        expected.setStatus("Ongoing");
        expected.setCover("//readcomicsonline.ru/uploads/manga/batman-2016/cover/cover_250x350.jpg");
        expected.setSummary("\"I AM GOTHAM\" Chapter One No one has ever stopped the Caped Crusader. Not The Joker. Not Two-Face. Not " +
            "even the entire Justice League. But how does Batman confront a new hero who wants to save the city from the Dark Knight? CAN'T MISS: Superstar " +
            "artist David Finch returns to Batman alongside writer Tom King for this five-part storyline.");

        assertThat(scrapedComic).isEqualToIgnoringGivenFields(expected, "issues");
        assertThat(scrapedComic.getIssues()).hasSize(95);

        assertThat(scrapedComic.getIssues()).containsAnyOf(
            new ScrapedIssueDetails("Batman (2016-) #33",
            "https://readcomicsonline.ru/comic/batman-2016/33", "18 Oct. 2017"),
            new ScrapedIssueDetails("Batman (2016-) #Annual 3",
                "https://readcomicsonline.ru/comic/batman-2016/Annual-3", "12 Dec. 2018"));
    }

    @Test
    public void testScrapeComicNotFound() {
        assertThatThrownBy(() -> readComicsScraper.scrapeComic("unknown"))
            .isInstanceOf(ComicNotFoundException.class)
            .hasMessageContaining("URL for unknown is not found");
    }

    @Test
    public void testScrapeIssueFound() throws IOException {
        //given
        ScrapedIssue scrapedIssue = readComicsScraper.scrapeIssue("batman-2016", "1");

        //then
        ScrapedIssue expected = new ScrapedIssue("batman-2016", "1", 25, Collections.emptyList());

        assertThat(scrapedIssue).isEqualToIgnoringGivenFields(expected, "pages");
        assertThat(scrapedIssue.getPages()).containsAnyOf("https://readcomicsonline.ru/uploads/manga/batman-2016/chapters/1/16.jpg");
        }

    @Test
    public void testScrapeIssueNotFound() {
        assertThatThrownBy(() -> readComicsScraper.scrapeIssue("unknown", "999"))
            .isInstanceOf(ComicNotFoundException.class)
            .hasMessageContaining("URL for unknown with issue 999 is not found");
    }

}
