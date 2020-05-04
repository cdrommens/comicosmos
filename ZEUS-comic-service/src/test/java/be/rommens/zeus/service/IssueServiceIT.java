package be.rommens.zeus.service;

import be.rommens.hera.api.Provider;
import be.rommens.zeus.model.event.DownloadIssue;
import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.configuration.Orthography;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.junit5.api.DBRider;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

/**
 * User : cederik
 * Date : 04/05/2020
 * Time : 21:16
 */
@Slf4j
@SpringBootTest
@DBRider
@DBUnit(caseInsensitiveStrategy = Orthography.LOWERCASE, cacheConnection = false)
@ActiveProfiles("container")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataSet(value = "datasets/comicservice/setup.yml")
public class IssueServiceIT {

    @Autowired
    private IssueService issueService;

    @MockBean
    private DomainEventPublisher publisher;

    @Test
    public void whenDownloadNewIssue_thenPublisherIsCalledOneTime() {
        DownloadIssue expected = new DownloadIssue();
        expected.setComicKey("batman-2016");
        expected.setProvider(Provider.READCOMICS);
        expected.setIssueId(-3);
        expected.setIssueNumber("Annual-1");
        expected.setDateOfRelease(LocalDate.of(2016, 2, 1));
        Integer result = issueService.downloadNewIssues();
        assertThat(result, Matchers.<Integer>is(1));
        then(publisher).should(times(1)).publish(expected);
    }
}
