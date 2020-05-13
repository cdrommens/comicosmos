package be.rommens.hermes.model;

import be.rommens.hera.api.Provider;
import be.rommens.hermes.model.input.IssueToDownload;
import be.rommens.hermes.model.input.IssueToDownloadList;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * User : cederik
 * Date : 13/05/2020
 * Time : 09:28
 */
public class IssueToDownloadListTestObjectFactory {

    public static IssueToDownloadList getIssueToDownloadListWithSingleIssue() {
        IssueToDownload issue = new IssueToDownload();
        issue.setIssueId(-3);
        issue.setComicKey("batman-2016");
        issue.setProvider(Provider.READCOMICS);
        issue.setIssueNumber("Annual-1");
        issue.setDateOfRelease(LocalDate.of(2016, 2, 1));
        List<IssueToDownload> internalList = Collections.singletonList(issue);
        IssueToDownloadList result = new IssueToDownloadList();
        result.setIssueToDownload(internalList);
        return result;
    }

    public static IssueToDownloadList getIssueToDownloadListWith2Issues() {
        IssueToDownload issue1 = new IssueToDownload();
        issue1.setIssueId(-3);
        issue1.setComicKey("batman-2016");
        issue1.setProvider(Provider.READCOMICS);
        issue1.setIssueNumber("Annual-1");
        issue1.setDateOfRelease(LocalDate.of(2016, 2, 1));
        IssueToDownload issue2 = new IssueToDownload();
        issue2.setIssueId(-4);
        issue2.setComicKey("batman-2016");
        issue2.setProvider(Provider.READCOMICS);
        issue2.setIssueNumber("1");
        issue2.setDateOfRelease(LocalDate.of(2018, 2, 1));
        List<IssueToDownload> internalList = Arrays.asList(issue1, issue2);
        IssueToDownloadList result = new IssueToDownloadList();
        result.setIssueToDownload(internalList);
        return result;
    }

    public static IssueToDownloadList getEmptyIssueToDownloadList() {
        List<IssueToDownload> internalList = Collections.emptyList();
        IssueToDownloadList result = new IssueToDownloadList();
        result.setIssueToDownload(internalList);
        return result;
    }
}
