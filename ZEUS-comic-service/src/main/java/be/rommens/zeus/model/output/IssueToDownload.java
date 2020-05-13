package be.rommens.zeus.model.output;

import be.rommens.hera.api.Provider;
import lombok.Data;

import java.time.LocalDate;

/**
 * User : cederik
 * Date : 12/05/2020
 * Time : 15:18
 */
@Data
public class IssueToDownload {

    private Integer issueId;
    private String comicKey;
    private Provider provider;
    private String issueNumber;
    private LocalDate dateOfRelease;
}
