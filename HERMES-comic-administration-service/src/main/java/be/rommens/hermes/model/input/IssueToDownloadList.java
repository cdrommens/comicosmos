package be.rommens.hermes.model.input;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * User : cederik
 * Date : 12/05/2020
 * Time : 15:42
 */
@Data
@NoArgsConstructor
public class IssueToDownloadList {

    private List<IssueToDownload> issueToDownload;
}
