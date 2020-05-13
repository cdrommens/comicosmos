package be.rommens.zeus.service;

import be.rommens.zeus.model.entity.Issue;
import be.rommens.zeus.repository.IssueRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * User : cederik
 * Date : 02/05/2020
 * Time : 14:35
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class IssueService {

    private final IssueRepository issueRepository;

    @Transactional(readOnly = true)
    public List<Issue> downloadNewIssues() {
        return issueRepository.findAllByDownloadedFalse();
    }
}
