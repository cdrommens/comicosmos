package be.rommens.hera.models;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * User : cederik
 * Date : 03/04/2020
 * Time : 20:30
 */
@Data
@RequiredArgsConstructor
public class ScrapedIssue {

    private final String comic;
    private final String issueNumber;
    private final Integer numberOfPages;
    private final List<String> pages;

}
