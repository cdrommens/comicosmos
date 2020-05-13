package be.rommens.hermes.model.input;

import be.rommens.hera.api.Provider;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * User : cederik
 * Date : 12/05/2020
 * Time : 15:18
 */
@Data
@NoArgsConstructor
public class IssueToDownload {

    private Integer issueId;
    private String comicKey;
    private Provider provider;
    private String issueNumber;
    private LocalDate dateOfRelease;
}
