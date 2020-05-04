package be.rommens.zeus.service;

import be.rommens.zeus.model.DownloadIssueConverter;
import be.rommens.zeus.model.Issue;
import be.rommens.zeus.repository.IssueRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * User : cederik
 * Date : 02/05/2020
 * Time : 14:35
 */
@Slf4j
@Service
public class IssueService {

    private final IssueRepository issueRepository;
    //TODO : make spring event publisher
    private final DomainEventPublisher domainEventsPublisher;

    public IssueService(IssueRepository issueRepository, DomainEventPublisher domainEventsPublisher) {
        this.issueRepository = issueRepository;
        this.domainEventsPublisher = domainEventsPublisher;
    }

    @Transactional(readOnly = true)
    public void downloadNewIssues() {
        List<Issue> issuesToDownload = issueRepository.findAllByDownloadedFalse();
        if (CollectionUtils.isEmpty(issuesToDownload)) {
            //TODO : what to return?
        }
        issuesToDownload.forEach(issue -> domainEventsPublisher.publish(new DownloadIssueConverter().convert(issue)));
    }
}
