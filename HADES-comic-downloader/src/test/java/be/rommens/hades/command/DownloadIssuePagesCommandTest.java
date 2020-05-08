package be.rommens.hades.command;

import be.rommens.hades.assembler.IssueAssemblyContext;
import be.rommens.hades.core.CommandResult;
import be.rommens.hades.model.IssueAssemblyContextTestObjectFactory;
import be.rommens.hera.api.models.ScrapedIssue;
import be.rommens.hera.builders.ScrapedIssueBuilder;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.cloud.contract.wiremock.WireMockSpring;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * User : cederik
 * Date : 27/04/2020
 * Time : 08:42
 */
public class DownloadIssuePagesCommandTest {

    public static WireMockServer wiremock = new WireMockServer(WireMockSpring.options().dynamicPort());

    @TempDir
    Path tempDir;

    @BeforeAll
    static void setupClass() {
        wiremock.start();
    }

    @AfterEach
    void after() {
        wiremock.resetAll();
    }

    @AfterAll
    static void clean() {
        wiremock.shutdown();
    }

    @Test
    public void whenIssueExists_thenReturnCompletedAndDownloadAllPages() throws IOException {
        //given
        File newDir = Paths.get(tempDir.toAbsolutePath().toString(), "comickey", "comickey-1").toFile();
        FileUtils.forceMkdir(newDir);

        ScrapedIssue scrapedIssue = new ScrapedIssueBuilder()
            .comic("comickey")
            .issueNumber("1")
            .numberOfPages(2)
            .addPage("http://localhost:" + wiremock.port() + "/page1.txt")
            .addPage("http://localhost:" + wiremock.port() + "/page2.txt")
            .build();
        IssueAssemblyContext context = IssueAssemblyContextTestObjectFactory.createTestContext(tempDir.toString(), null);
        context.setScrapedIssue(scrapedIssue);
        DownloadIssuePagesCommand command = new DownloadIssuePagesCommand(context);

        //when
        CommandResult result = command.body();

        //then
        assertThat(Files.exists(Paths.get(tempDir.toAbsolutePath().toString(), "comickey", "comickey-1"))).isTrue();
        assertThat(result).isEqualTo(CommandResult.COMPLETED);
        assertThat(Files.exists(Paths.get(tempDir.toAbsolutePath().toString(), "comickey", "comickey-1", "page1.txt"))).isTrue();
        assertThat(Files.exists(Paths.get(tempDir.toAbsolutePath().toString(), "comickey", "comickey-1", "page2.txt"))).isTrue();
    }

    @Test
    public void whenIssueNotExists_thenReturnErrorAndFilesNotDownloaded() throws IOException {
        //given
        File newDir = Paths.get(tempDir.toAbsolutePath().toString(), "comickey", "comickey-1").toFile();
        FileUtils.forceMkdir(newDir);

        ScrapedIssue scrapedIssue = new ScrapedIssueBuilder()
            .comic("comickey")
            .issueNumber("1")
            .numberOfPages(2)
            .addPage("http://localhost:" + wiremock.port() + "/page1.txt")
            .addPage("http://localhost:" + wiremock.port() + "/page2.txt")
            .addPage("http://localhost:" + wiremock.port() + "/unknownpage.txt")
            .build();
        IssueAssemblyContext context = IssueAssemblyContextTestObjectFactory.createTestContext(tempDir.toString(), null);
        context.setScrapedIssue(scrapedIssue);
        DownloadIssuePagesCommand command = new DownloadIssuePagesCommand(context);

        //when
        CommandResult result = command.body();

        //then
        assertThat(Files.exists(Paths.get(tempDir.toAbsolutePath().toString(), "comickey", "comickey-1"))).isTrue();
        assertThat(result).isEqualTo(CommandResult.ERROR);
    }
}
