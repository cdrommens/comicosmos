package be.rommens.zeus.poc;

import be.rommens.hera.core.Scraper;
import be.rommens.zeus.model.Issue;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * User : cederik
 * Date : 22/04/2020
 * Time : 16:05
 */
@Slf4j
public class ScrapeIssueCommand extends AbstractCommand {

    private final Scraper scraper;
    private final Issue issue;

    ScrapeIssueCommand(AssembleIssueContext assembleIssueContext) {
        super(assembleIssueContext);
        this.scraper = assembleIssueContext.getScraper();
        this.issue = assembleIssueContext.getIssue();
    }

    @Override
    public CommandResult body() {
        try {
            assembleIssueContext.setScrapedIssue(scraper.scrapeIssue(issue.getComic().getKey(), issue.getIssueNumber()));
            log.info("   [GetPages] Pages fetched for {} issue {}", issue.getComic().getName(), issue.getIssueNumber());
            return CommandResult.COMPLETED;
        } catch (IOException e) {
            log.info("   [GetPages] something went wrong", e);
            return CommandResult.ERROR;
        }
    }
}
