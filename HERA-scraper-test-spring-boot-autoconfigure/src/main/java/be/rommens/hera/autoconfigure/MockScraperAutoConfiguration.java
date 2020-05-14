package be.rommens.hera.autoconfigure;

import be.rommens.hera.ScraperFactoryMock;
import be.rommens.hera.ScraperMock;
import be.rommens.hera.api.models.ScrapedIssue;
import be.rommens.hera.api.service.ScraperFactory;
import be.rommens.hera.builders.ScrapedIssueBuilder;
import be.rommens.hera.core.Scraper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * User : cederik
 * Date : 04/04/2020
 * Time : 14:46
 */
//TODO : read yaml file
@Configuration
public class MockScraperAutoConfiguration {

    private Scraper createMockScraper() {
        ScrapedIssue scrapedIssue = new ScrapedIssueBuilder()
            .comic("comickey")
            .issueNumber("1")
            .numberOfPages(2)
            .addPage("page1")
            .addPage("page2")
            .build();
        ScraperMock scraperMock = new ScraperMock(null);
        scraperMock.setExpectedScrapedIssue(scrapedIssue);
        return scraperMock;
    }

    @Bean
    public ScraperFactory createScraperFactoryMock() {
        return new ScraperFactoryMock(createMockScraper());
    }

}
