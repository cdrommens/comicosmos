package be.rommens.zeus.service;

import be.rommens.zeus.model.event.DownloadIssue;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.stereotype.Component;

/**
 * User : cederik
 * Date : 30/04/2020
 * Time : 15:44
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DownloadIssuePublisher {

    private final Source source;

    public DownloadIssue sendDownloadIssueMessage() {
        return new DownloadIssue();
    }
}
