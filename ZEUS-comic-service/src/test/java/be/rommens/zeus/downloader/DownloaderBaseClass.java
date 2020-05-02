package be.rommens.zeus.downloader;

import be.rommens.hera.api.Provider;
import be.rommens.hera.api.Status;
import be.rommens.zeus.ZeusApplication;
import be.rommens.zeus.model.Comic;
import be.rommens.zeus.model.Issue;
import be.rommens.zeus.model.builder.ComicBuilder;
import be.rommens.zeus.model.builder.IssueBuilder;
import be.rommens.zeus.repository.IssueRepository;
import be.rommens.zeus.service.IssueService;
import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.contract.verifier.messaging.MessageVerifier;
import org.springframework.cloud.contract.verifier.messaging.boot.AutoConfigureMessageVerifier;

import java.util.concurrent.TimeUnit;

/**
 * User : cederik
 * Date : 30/04/2020
 * Time : 15:36
 */
@SpringBootTest(classes = ZeusApplication.class,
    webEnvironment = SpringBootTest.WebEnvironment.NONE)
@AutoConfigureMessageVerifier
public class DownloaderBaseClass {

    @Autowired
    private MessageVerifier messaging;

    @MockBean
    private IssueRepository issueRepository;

    @Autowired
    private IssueService issueService;

    @BeforeEach
    public void setup() {
        // let's clear any remaining messages
        // output == destination or channel name
        this.messaging.receive("downloader-in", 100, TimeUnit.MILLISECONDS);
        Comic comic = ComicBuilder.aComic()
            .key("comickey")
            .provider(Provider.READCOMICS)
            .status(Status.ONGOING)
            .build();
        Issue issue = IssueBuilder.anIssue()
            .issueId(1)
            .issueNumber("1")
            .build();
        comic.addIssue(issue);
        Mockito.when(this.issueRepository.findAllByDownloadedFalse()).thenReturn(ImmutableList.of(issue));
    }

    public void downloadIssue() {
        issueService.downloadNewIssues();
    }
}
