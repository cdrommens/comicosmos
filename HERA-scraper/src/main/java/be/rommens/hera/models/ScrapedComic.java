package be.rommens.hera.models;

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
    private String type;
    private String author;
    private String dateOfRelease;
    private String status;
    private String cover;
    private String summary;
    @Setter(value = AccessLevel.NONE)
    private Set<ScrapedIssue> issues = new HashSet<>();

    public void addIssue(ScrapedIssue issue) {
        issues.add(issue);
    }
}
