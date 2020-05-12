package be.rommens.hades.connectivity;

import be.rommens.hera.api.Provider;
import lombok.Data;

import javax.annotation.Nonnull;
import java.time.LocalDate;

/**
 * User : cederik
 * Date : 26/04/2020
 * Time : 13:46
 */
@Data
public class DownloadIssueMessage {

    @Nonnull
    private Integer issueId;
    @Nonnull
    private String comicKey;
    @Nonnull
    private Provider provider;
    @Nonnull
    private String issueNumber;
    private LocalDate dateOfRelease;

    public String getIssueFolder() {
        return comicKey + "-" + issueNumber;
    }

    public String getComicFolder() {
        return comicKey;
    }
}
