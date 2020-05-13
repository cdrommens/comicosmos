package be.rommens.zeus.model.output;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * User : cederik
 * Date : 12/05/2020
 * Time : 15:37
 */
@Data
public class IssueToDownloadList {

    private final List<IssueToDownload> issueToDownload = new ArrayList<>();

    public void add(IssueToDownload item) {
        issueToDownload.add(item);
    }

}
