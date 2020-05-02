package be.rommens.zeus.model.builder;

import be.rommens.zeus.model.Comic;
import be.rommens.zeus.model.Issue;

import java.time.LocalDate;

/**
 * User : cederik
 * Date : 17/04/2020
 * Time : 11:26
 */
public final class IssueBuilder {
    private Integer issueId;
    private Comic comic;
    private String issueNumber;
    private LocalDate dateOfRelease;
    private Boolean downloaded;

    private IssueBuilder() {
    }

    public static IssueBuilder anIssue() {
        return new IssueBuilder();
    }

    public IssueBuilder issueId(Integer issueId) {
        this.issueId = issueId;
        return this;
    }

    public IssueBuilder comic(Comic comic) {
        this.comic = comic;
        return this;
    }

    public IssueBuilder issueNumber(String issueNumber) {
        this.issueNumber = issueNumber;
        return this;
    }

    public IssueBuilder dateOfRelease(LocalDate dateOfRelease) {
        this.dateOfRelease = dateOfRelease;
        return this;
    }

    public IssueBuilder downloaded(Boolean downloaded) {
        this.downloaded = downloaded;
        return this;
    }

    public Issue build() {
        Issue issue = new Issue();
        issue.setIssueId(issueId);
        issue.setComic(comic);
        issue.setIssueNumber(issueNumber);
        issue.setDateOfRelease(dateOfRelease);
        issue.setDownloaded(downloaded);
        return issue;
    }
}
