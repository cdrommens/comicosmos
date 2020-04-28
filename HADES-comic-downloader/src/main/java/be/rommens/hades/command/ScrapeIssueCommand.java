package be.rommens.hades.command;

import be.rommens.hades.assembler.Issue;
import be.rommens.hades.assembler.IssueAssemblyContext;
import be.rommens.hades.core.CommandResult;
import be.rommens.hera.core.Scraper;
import lombok.extern.slf4j.Slf4j;

/**
 * User : cederik
 * Date : 22/04/2020
 * Time : 16:05
 */
@Slf4j
public class ScrapeIssueCommand extends AbstractCommand {

    private final Scraper scraper;
    private final Issue issue;

    public ScrapeIssueCommand(IssueAssemblyContext issueAssemblyContext) {
        super(issueAssemblyContext);
        this.scraper = issueAssemblyContext.getScraper();
        this.issue = issueAssemblyContext.getIssue();
    }

    @Override
    public CommandResult body() {
        try {
            issueAssemblyContext.setScrapedIssue(scraper.scrapeIssue(issue.getComicKey(), issue.getIssueNumber()));
            log.info("   [GetPages] Pages fetched for {} issue {}", issue.getComicKey(), issue.getIssueNumber());
            return CommandResult.COMPLETED;
        } catch (Exception e) {
            log.info("   [GetPages] something went wrong", e);
            return CommandResult.ERROR;
        }
    }
}
