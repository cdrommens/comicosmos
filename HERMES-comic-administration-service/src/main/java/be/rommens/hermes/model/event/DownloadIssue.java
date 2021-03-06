package be.rommens.hermes.model.event;

import be.rommens.hera.api.Provider;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.time.LocalDate;

/**
 * User : cederik
 * Date : 30/04/2020
 * Time : 15:49
 */
@Data
public class DownloadIssue implements DomainEvent {

    private Integer issueId;
    private String comicKey;
    private Provider provider;
    private String issueNumber;
    private LocalDate dateOfRelease;

    @Override
    @JsonIgnore
    public String getType() {
        return "download-issue";
    }
}
