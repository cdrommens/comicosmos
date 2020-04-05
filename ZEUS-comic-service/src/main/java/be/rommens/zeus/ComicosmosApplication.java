package be.rommens.zeus;

import be.rommens.hera.api.Provider;
import be.rommens.hera.api.models.ScrapedComic;
import be.rommens.hera.api.service.ScraperFactory;
import be.rommens.hera.core.Scraper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@SpringBootApplication
@RestController
public class ComicosmosApplication {

    private final ScraperFactory dependency;

    public ComicosmosApplication(ScraperFactory dependency) {
        this.dependency = dependency;
    }

    @GetMapping("/")
    public String home() {
        Scraper scraper = dependency.createScraper(Provider.EXAMPLE);
        try {
            ScrapedComic scrapedComic = scraper.scrapeComic("batman-2016");
            System.out.println(scrapedComic.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Hello world! - ";
    }

    public static void main(String[] args) {
        SpringApplication.run(ComicosmosApplication.class, args);
    }

}
