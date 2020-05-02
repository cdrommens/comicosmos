package be.rommens.hades.assembler;

import be.rommens.hera.api.models.ScrapedIssue;
import be.rommens.hera.core.Scraper;

/**
 * User : cederik
 * Date : 20/04/2020
 * Time : 19:52
 */
public class IssueAssemblyContext {

    private final DownloadIssueMessage downloadIssueMessage;
    private final String baseUrl;
    private final Scraper scraper;

    private String issueFolder;
    private ScrapedIssue scrapedIssue;

    public IssueAssemblyContext(DownloadIssueMessage downloadIssueMessage, String baseUrl, Scraper scraper) {
        this.downloadIssueMessage = downloadIssueMessage;
        this.baseUrl = baseUrl;
        this.scraper = scraper;
    }

    public DownloadIssueMessage getDownloadIssueMessage() {
        return downloadIssueMessage;
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
