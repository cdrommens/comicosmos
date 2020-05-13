package be.rommens.hermes.model.event;

import be.rommens.hermes.model.input.IssueToDownload;
import org.springframework.core.convert.converter.Converter;

/**
 * User : cederik
 * Date : 02/05/2020
 * Time : 14:09
 */
public class DownloadIssueConverter implements Converter<IssueToDownload, DownloadIssue> {

    @Override
    public DownloadIssue convert(IssueToDownload issue) {
        DownloadIssue downloadIssue = new DownloadIssue();
        downloadIssue.setComicKey(issue.getComicKey());
        downloadIssue.setIssueId(issue.getIssueId());
        downloadIssue.setProvider(issue.getProvider());
        downloadIssue.setDateOfRelease(issue.getDateOfRelease());
        downloadIssue.setIssueNumber(issue.getIssueNumber());
        return downloadIssue;
    }
}
