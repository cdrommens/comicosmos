package be.rommens.hades.command;

import be.rommens.hades.assembler.IssueAssemblyContext;
import be.rommens.hades.core.CommandResult;
import be.rommens.hades.model.IssueAssemblyContextTestObjectFactory;
import be.rommens.hera.ScraperTestFactory;
import be.rommens.hera.api.models.ScrapedIssue;
import be.rommens.hera.builders.ScrapedIssueBuilder;
import be.rommens.hera.core.Scraper;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * User : cederik
 * Date : 26/04/2020
 * Time : 14:35
 */
public class ScrapeIssueCommandTest {

    @Test
    public void whenIssueExists_thenReturnCompletedAndScrapedIssue() {
        //given
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

        //when
        CommandResult result = command.body();

        //then
        assertThat(result).isEqualTo(CommandResult.COMPLETED);
        assertThat(context.getScrapedIssue()).usingRecursiveComparison().isEqualTo(scrapedIssue);
    }

    @Test
    public void whenIssueNotExists_thenReturnErrorAndNoScrapedIssue() {
        //given
        Scraper scraper = ScraperTestFactory.willThrowComicNotFound("comickey");
        IssueAssemblyContext context = IssueAssemblyContextTestObjectFactory.createTestContext(null, scraper);
        ScrapeIssueCommand command = new ScrapeIssueCommand(context);

        //when
        CommandResult result = command.body();

        //then
        assertThat(result).isEqualTo(CommandResult.ERROR);
        assertThat(context.getScrapedIssue()).isNull();
    }
}
