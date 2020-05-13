package be.rommens.hermes.service;

import be.rommens.hermes.connectivity.ZeusConnectorService;
import be.rommens.hermes.model.event.DownloadIssueConverter;
import be.rommens.hermes.model.input.IssueToDownload;
import be.rommens.hermes.model.input.IssueToDownloadList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

/**
 * User : cederik
 * Date : 12/05/2020
 * Time : 15:07
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class IssueService {

    private final ZeusConnectorService zeusConnectorService;
    //TODO : make spring event publisher
    private final DomainEventPublisher domainEventsPublisher;

    public Integer downloadNewIssues() {
        int numberOfScheduledComics = 0;
        IssueToDownloadList issueToDownloadList = zeusConnectorService.getIssuesToDownload();
        if (CollectionUtils.isEmpty(issueToDownloadList.getIssueToDownload())) {
            return numberOfScheduledComics;
        }
        for (IssueToDownload issue : issueToDownloadList.getIssueToDownload()) {
            domainEventsPublisher.publish(new DownloadIssueConverter().convert(issue));
            numberOfScheduledComics++;
        }
        return numberOfScheduledComics;
    }
}
