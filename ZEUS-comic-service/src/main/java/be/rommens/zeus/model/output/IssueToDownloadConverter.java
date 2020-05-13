package be.rommens.zeus.model.output;

import be.rommens.zeus.model.entity.Issue;
import org.springframework.core.convert.converter.Converter;

/**
 * User : cederik
 * Date : 12/05/2020
 * Time : 15:21
 */
public class IssueToDownloadConverter implements Converter<Issue, IssueToDownload> {

    @Override
    public IssueToDownload convert(Issue issue) {
        IssueToDownload issueToDownload = new IssueToDownload();
        issueToDownload.setIssueId(issue.getIssueId());
        issueToDownload.setComicKey(issue.getComic().getKey());
        issueToDownload.setProvider(issue.getComic().getProvider());
        issueToDownload.setIssueNumber(issue.getIssueNumber());
        issueToDownload.setDateOfRelease(issue.getDateOfRelease());
        return issueToDownload;
    }
}
