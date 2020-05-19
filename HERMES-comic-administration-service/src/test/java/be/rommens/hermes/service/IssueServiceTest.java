package be.rommens.hermes.service;

import be.rommens.hera.api.Provider;
import be.rommens.hermes.connectivity.ZeusConnectorService;
import be.rommens.hermes.model.event.DownloadIssue;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static be.rommens.hermes.model.IssueToDownloadListTestObjectFactory.getEmptyIssueToDownloadList;
import static be.rommens.hermes.model.IssueToDownloadListTestObjectFactory.getIssueToDownloadListWithSingleIssue;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

/**
 * User : cederik
 * Date : 04/05/2020
 * Time : 21:16
 */
@Slf4j
@ExtendWith(MockitoExtension.class)
class IssueServiceTest {

    @Mock
    private DomainEventPublisher publisher;

    @Mock
    private ZeusConnectorService zeusConnectorService;

    @InjectMocks
    private IssueService issueService;

    @Test
    void whenIssuesFound_thenPublisherIsCalledOneTime() {
        //given
        given(zeusConnectorService.getIssuesToDownload()).willReturn(getIssueToDownloadListWithSingleIssue());

        DownloadIssue expected = new DownloadIssue();
        expected.setComicKey("batman-2016");
        expected.setProvider(Provider.READCOMICS);
        expected.setIssueId(-3);
        expected.setIssueNumber("Annual-1");
        expected.setDateOfRelease(LocalDate.of(2016, 2, 1));

        //when
        Integer result = issueService.downloadNewIssues();

        //then
        assertThat(result).isEqualTo(1);
        then(publisher).should(times(1)).publish(expected);
    }

    @Test
    void whenNoIssuesFound_thenPublisherIsNotCalled() {
        //given
        given(zeusConnectorService.getIssuesToDownload()).willReturn(getEmptyIssueToDownloadList());

        //when
        Integer result = issueService.downloadNewIssues();

        //then
        assertThat(result).isEqualTo(0);
        then(publisher).shouldHaveNoInteractions();
    }
}
