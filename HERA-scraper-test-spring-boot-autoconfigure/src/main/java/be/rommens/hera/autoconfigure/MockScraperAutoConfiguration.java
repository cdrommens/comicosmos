package be.rommens.hera.autoconfigure;

import be.rommens.hera.ScraperFactoryMock;
import be.rommens.hera.ScraperMock;
import be.rommens.hera.api.service.ScraperFactory;
import be.rommens.hera.core.Scraper;
import be.rommens.hera.dataset.Comic;
import be.rommens.hera.dataset.DataSetParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.List;

/**
 * User : cederik
 * Date : 04/04/2020
 * Time : 14:46
 */
//TODO : read yaml file
@Configuration
@RequiredArgsConstructor
@Slf4j
public class MockScraperAutoConfiguration {

    private final Environment environment;

    private Scraper createMockScraper(List<Comic> givenResults) {
        ScraperMock scraperMock = new ScraperMock(null, givenResults);
        return scraperMock;
    }

    @Bean
    public ScraperFactory createScraperFactoryMock() {
        String dataset = this.environment.getProperty("hera.test.scrapermock.value");
        log.info("dataset = {}", dataset);
        List<Comic> parsedComics = processDataSet(dataset);
        return new ScraperFactoryMock(createMockScraper(parsedComics));
    }

    private List<Comic> processDataSet(String dataset) {
        DataSetParser parser = new DataSetParser(dataset);
        return parser.parseDataSet();
    }

}
