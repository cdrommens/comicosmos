package be.rommens.zeus.service;

import be.rommens.zeus.model.entity.Issue;
import be.rommens.zeus.model.event.DownloadIssueConverter;
import be.rommens.zeus.repository.IssueRepository;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class IssueService {

    private final IssueRepository issueRepository;
    //TODO : make spring event publisher
    private final DomainEventPublisher domainEventsPublisher;

    @Transactional(readOnly = true)
    public Integer downloadNewIssues() {
        int numberOfScheduledComics = 0;
        List<Issue> issuesToDownload = issueRepository.findAllByDownloadedFalse();
        if (CollectionUtils.isEmpty(issuesToDownload)) {
            return numberOfScheduledComics;
        }
        for (Issue issue : issuesToDownload) {
            domainEventsPublisher.publish(new DownloadIssueConverter().convert(issue));
            numberOfScheduledComics++;
        }
        return numberOfScheduledComics;
    }
}
