package be.rommens.hades.command;

import be.rommens.hades.assembler.IssueAssemblyContext;
import be.rommens.hades.core.CommandResult;
import be.rommens.hades.model.IssueAssemblyContextTestObjectFactory;
import be.rommens.hera.ScraperTestFactory;
import be.rommens.hera.api.models.ScrapedIssue;
import be.rommens.hera.builders.ScrapedIssueBuilder;
import be.rommens.hera.core.Scraper;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

/**
 * User : cederik
 * Date : 26/04/2020
 * Time : 14:35
 */
public class ScrapeIssueCommandTest {

    @Test
    public void whenIssueExists_thenReturnCompletedAndScrapedIssue() {
        ScrapedIssue scrapedIssue = new ScrapedIssueBuilder()
            .comic("comickey")
            .issueNumber("1")
            .numberOfPages(2)
            .addPage("page1")
            .addPage("page2")
            .build();
        Scraper scraper = ScraperTestFactory.willReturnScrapedIssue(scrapedIssue);
        IssueAssemblyContext context = IssueAssemblyContextTestObjectFactory.createTestContext(null, scraper);
        ScrapeIssueCommand command = new ScrapeIssueCommand(context);
        CommandResult result = command.body();
        assertThat(result, is(CommandResult.COMPLETED));
        assertThat(context.getScrapedIssue(), is(scrapedIssue));
    }

    @Test
    public void whenIssueNotExists_thenReturnErrorAndNoScrapedIssue() {
        Scraper scraper = ScraperTestFactory.willThrowComicNotFound("comickey");
        IssueAssemblyContext context = IssueAssemblyContextTestObjectFactory.createTestContext(null, scraper);
        ScrapeIssueCommand command = new ScrapeIssueCommand(context);
        CommandResult result = command.body();
        assertThat(result, is(CommandResult.ERROR));
        assertThat(context.getScrapedIssue(), is(nullValue()));
    }
}
