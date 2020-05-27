package be.rommens.hermes.service;

/**
 * User : cederik
 * Date : 27/05/2020
 * Time : 13:00
 */
public interface IssueService {
    Integer downloadNewIssues();

    Integer downloadSignleIssue(String comic, String issue);
}
