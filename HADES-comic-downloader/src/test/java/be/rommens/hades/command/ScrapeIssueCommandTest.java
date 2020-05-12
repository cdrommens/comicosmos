package be.rommens.hades.command;

import be.rommens.hades.assembler.IssueAssemblyContext;
import be.rommens.hades.core.CommandResult;
import be.rommens.hades.model.IssueAssemblyContextTestObjectFactory;
import be.rommens.hera.ScraperTestFactory;
import be.rommens.hera.api.models.ScrapedIssue;
import be.rommens.hera.builders.ScrapedIssueBuilder;
import be.rommens.hera.core.Scraper;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * User : cederik
 * Date : 26/04/2020
 * Time : 14:35
 */
public class ScrapeIssueCommandTest {

    @TempDir
    Path tempDir;

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

    @Test
    public void testRollback() throws IOException {
        //given
        File newDir = Paths.get(tempDir.toAbsolutePath().toString(), "comickey", "comickey-1").toFile();
        FileUtils.forceMkdir(newDir);
        ScrapeIssueCommand command = new ScrapeIssueCommand(IssueAssemblyContextTestObjectFactory.createTestContext(tempDir.toString(), null));
        //when
        boolean result = command.rollback();
        //then
        assertThat(result).isTrue();
        assertThat(Files.exists(newDir.toPath())).isFalse();
    }
}
