package be.rommens.hades.connectivity;

import be.rommens.hera.api.Provider;
import lombok.Data;

import java.time.LocalDate;

/**
 * User : cederik
 * Date : 26/04/2020
 * Time : 13:46
 */
//TODO : javax.validation
@Data
public class DownloadIssueMessage {

    private Integer issueId;
    private String comicKey;
    private Provider provider;
    private String issueNumber;
    private LocalDate dateOfRelease;

    public String getIssueFolder() {
        return comicKey + "-" + issueNumber;
    }

    public String getComicFolder() {
        return comicKey;
    }
}
