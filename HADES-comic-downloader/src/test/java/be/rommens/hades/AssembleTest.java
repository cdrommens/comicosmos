package be.rommens.hades;

import org.springframework.boot.test.context.SpringBootTest;

/**
 * User : cederik
 * Date : 22/04/2020
 * Time : 15:12
 */
@SpringBootTest
public class AssembleTest {

    /*
    @Autowired
    private IssueAssembler issueAssembler;

    @MockBean
    private ScraperFactory scraperFactory;

    @Test
    public void test() {
        IssueBuilder issueBuilder = IssueBuilder.anIssue()
            .issueId(-1)
            .issueNumber("1")
            .dateOfRelease(LocalDate.of(2016,1,1))
            .url("url1");
        Comic comic = ComicBuilder.aComic()
            .provider(Provider.READCOMICS)
            .key("batman-2016")
            .name("Batman (2016-)")
            .publisher(PublisherTestObjectFactory.getDcComicsPublisher())
            .status(Status.ONGOING)
            .dateOfRelease(LocalDate.of(2016,1,1))
            .author("Tom King")
            .description("\"I AM GOTHAM\" Chapter One No one has ever stopped the Caped Crusader. Not The Joker. Not Two-Face. Not even the entire Justice League. But how does Batman confront a new hero who wants to save the city from the Dark Knight? CAN'T MISS: Superstar artist David Finch returns to Batman alongside writer Tom King for this five-part storyline.")
            .issue(issueBuilder)
            .build();
        Issue issue = issueBuilder.build();
        issue.setComic(comic);

        BDDMockito.given(scraperFactory.createScraper(any())).willReturn(ScraperTestFactory.willReturnScrapedIssue(
            new ScrapedIssueBuilder()
                .numberOfPages(2)
                .addPage("https://www.google.be/images/branding/googlelogo/2x/googlelogo_color_272x92dp.png")
                .addPage("https://s.yimg.com/rz/p/yahoo_frontpage_en-US_s_f_p_205x58_frontpage_2x.png")
                .build()
        ));

        issueAssembler.addToQueue(issue);
        issueAssembler.scheduledTask();
    }
    */
}