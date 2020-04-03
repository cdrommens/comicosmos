package be.rommens.hera.core.models;

import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * User : cederik
 * Date : 02/04/2020
 * Time : 20:16
 */
@Data
@RequiredArgsConstructor
public class ScrapedIssueDetails {

    private final String issue;
    private final String url;
    private final String date;

}
