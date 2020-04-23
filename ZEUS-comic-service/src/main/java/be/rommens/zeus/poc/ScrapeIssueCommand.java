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

    //TODO : zorg dat dit stukje altijd in de abstract wordt uitgevoerd (nextExecute()) en dat deze methode enkel de body is
    @Override
    public boolean execute() {
        try {
            assembleIssueContext.setScrapedIssue(scraper.scrapeIssue(issue.getComic().getKey(), issue.getIssueNumber()));
            log.info("   [GetPages] Pages fetched for {} issue {}", issue.getComic().getName(), issue.getIssueNumber());
            return nextExecute();
        } catch (IOException e) {
            log.info("   [GetPages] something went wrong", e);
            return false;
        }
    }
}
