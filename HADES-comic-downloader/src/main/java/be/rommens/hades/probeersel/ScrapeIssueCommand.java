package be.rommens.hades.probeersel;

import be.rommens.hades.assembler.DownloadIssueMessage;
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
    private final DownloadIssueMessage downloadIssueMessage;

    public ScrapeIssueCommand(IssueAssemblyContext issueAssemblyContext) {
        super(issueAssemblyContext);
        this.scraper = issueAssemblyContext.getScraper();
        this.downloadIssueMessage = issueAssemblyContext.getDownloadIssueMessage();
    }

    @Override
    public CommandResult body() {
        try {
            issueAssemblyContext.setScrapedIssue(scraper.scrapeIssue(downloadIssueMessage.getComicKey(), downloadIssueMessage.getIssueNumber()));
            log.info("   [GetPages] Pages fetched for {} issue {}", downloadIssueMessage.getComicKey(), downloadIssueMessage.getIssueNumber());
            return CommandResult.COMPLETED;
        } catch (Exception e) {
            log.info("   [GetPages] something went wrong", e);
            return CommandResult.ERROR;
        }
    }
}
