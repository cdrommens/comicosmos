package be.rommens.hermes.downloader;

import be.rommens.hermes.HermesApplication;
import be.rommens.hermes.connectivity.ZeusConnectorService;
import be.rommens.hermes.service.IssueService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.contract.verifier.messaging.MessageVerifier;
import org.springframework.cloud.contract.verifier.messaging.boot.AutoConfigureMessageVerifier;

import java.util.concurrent.TimeUnit;

import static be.rommens.hermes.model.IssueToDownloadListTestObjectFactory.getIssueToDownloadListWithSingleIssue;
import static org.mockito.BDDMockito.given;

/**
 * User : cederik
 * Date : 30/04/2020
 * Time : 15:36
 */
@SpringBootTest(classes = HermesApplication.class,
    webEnvironment = SpringBootTest.WebEnvironment.NONE)
@AutoConfigureMessageVerifier
public class DownloaderBaseClass {

    @Autowired
    private MessageVerifier messaging;

    @MockBean
    private ZeusConnectorService zeusConnectorService;

    @Autowired
    private IssueService issueService;

    @BeforeEach
    public void setup() {
        // let's clear any remaining messages
        // output == destination or channel name
        this.messaging.receive("downloader-in", 100, TimeUnit.MILLISECONDS);
        given(this.zeusConnectorService.getIssuesToDownload()).willReturn(getIssueToDownloadListWithSingleIssue());
    }

    public void downloadIssue() {
        issueService.downloadNewIssues();
    }
}
