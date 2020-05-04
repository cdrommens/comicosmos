package be.rommens.zeus.model.event;

import be.rommens.zeus.model.entity.Issue;
import org.springframework.core.convert.converter.Converter;

/**
 * User : cederik
 * Date : 02/05/2020
 * Time : 14:09
 */
public class DownloadIssueConverter implements Converter<Issue, DownloadIssue> {

    @Override
    public DownloadIssue convert(Issue issue) {
        DownloadIssue downloadIssue = new DownloadIssue();
        downloadIssue.setComicKey(issue.getComic().getKey());
        downloadIssue.setIssueId(issue.getIssueId());
        downloadIssue.setProvider(issue.getComic().getProvider());
        downloadIssue.setDateOfRelease(issue.getDateOfRelease());
        downloadIssue.setIssueNumber(issue.getIssueNumber());
        return downloadIssue;
    }
}
