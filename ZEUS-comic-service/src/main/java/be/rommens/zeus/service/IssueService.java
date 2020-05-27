package be.rommens.zeus.service;

import be.rommens.zeus.model.entity.Issue;

import java.util.List;

/**
 * User : cederik
 * Date : 27/05/2020
 * Time : 13:55
 */
public interface IssueService {

    List<Issue> downloadNewIssues();
}
