package be.rommens.zeus.poc;

import be.rommens.hera.api.models.ScrapedIssue;
import be.rommens.hera.core.Scraper;
import be.rommens.zeus.model.Issue;

/**
 * User : cederik
 * Date : 20/04/2020
 * Time : 19:52
 */
public class AssembleIssueContext {

    private final Issue issue;
    private final String baseUrl;
    private final Scraper scraper;

    private String issueFolder;
    private ScrapedIssue scrapedIssue;

    public AssembleIssueContext(Issue issue, String baseUrl, Scraper scraper) {
        this.issue = issue;
        this.baseUrl = baseUrl;
        this.scraper = scraper;
    }

    public Issue getIssue() {
        return issue;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getIssueFolder() {
        return issueFolder;
    }

    public void setIssueFolder(String issueFolder) {
        this.issueFolder = issueFolder;
    }

    public ScrapedIssue getScrapedIssue() {
        return scrapedIssue;
    }

    public void setScrapedIssue(ScrapedIssue scrapedIssue) {
        this.scrapedIssue = scrapedIssue;
    }

    public Scraper getScraper() {
        return scraper;
    }
}
