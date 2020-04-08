package be.rommens.hera.api.models;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

/**
 * User : cederik
 * Date : 02/04/2020
 * Time : 20:04
 */

@Data
public class ScrapedComic {

    private String title;
    private String publisher;
    private String author;
    private String dateOfRelease;
    private String status;          //TODO : after inventory, convert to enum
    private String cover;
    private String summary;
    @Setter(value = AccessLevel.NONE)
    private Set<ScrapedIssueDetails> issues = new HashSet<>();

    public void addIssue(ScrapedIssueDetails issue) {
        issues.add(issue);
    }
}
