package be.rommens.zeus.web;

import be.rommens.hera.api.Provider;
import be.rommens.hera.api.Status;
import be.rommens.hera.api.models.ScrapedComic;
import be.rommens.hera.api.service.ScraperFactory;
import be.rommens.hera.core.Scraper;
import be.rommens.zeus.model.Comic;
import be.rommens.zeus.model.Publisher;
import be.rommens.zeus.model.builder.ComicBuilder;
import be.rommens.zeus.model.builder.IssueBuilder;
import be.rommens.zeus.repository.PublisherRepository;
import be.rommens.zeus.service.ComicService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.LocalDate;

/**
 * User : cederik
 * Date : 09/04/2020
 * Time : 14:49
 */
@RestController
public class IndexController {

    private final ScraperFactory dependency;

    private final PublisherRepository repository;
    private final ComicService comicService;

    public IndexController(ScraperFactory dependency, PublisherRepository repository, ComicService comicService) {
        this.dependency = dependency;
        this.repository = repository;
        this.comicService = comicService;
    }

    @GetMapping("/")
    public String home() {
        Scraper scraper = dependency.createScraper(Provider.EXAMPLE);
        try {
            ScrapedComic scrapedComic = scraper.scrapeComic("batman-2016");
            System.out.println("Result : " + scrapedComic.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        Publisher p = repository.findById(1).orElseThrow(() -> new IllegalStateException("dd"));
        Comic c = ComicBuilder.aComic()
            .key("batman-2016")
            .name("Batman (2016-)")
            .publisher(p)
            .status(Status.ONGOING)
            .dateOfRelease(LocalDate.of(2016,1,1))
            .author("Tom King")
            .description("\"I AM GOTHAM\" Chapter One No one has ever stopped the Caped Crusader. Not The Joker. Not Two-Face. Not even the entire Justice League. But how does Batman confront a new hero who wants to save the city from the Dark Knight? CAN'T MISS: Superstar artist David Finch returns to Batman alongside writer Tom King for this five-part storyline.")
            .issue(IssueBuilder.anIssue()
                .issueNumber("1")
                .dateOfRelease(LocalDate.of(2016,1,1))
                .downloaded(true))
            .issue(IssueBuilder.anIssue()
                .issueNumber("Annual-1")
                .dateOfRelease(LocalDate.of(2016,2,1))
                .downloaded(false))
            .issue(IssueBuilder.anIssue()
                .issueNumber("2")
                .dateOfRelease(LocalDate.of(2016,3,1))
                .downloaded(true))
            .build();

        comicService.save(c);


        return "Hello world! - ";
    }
}
