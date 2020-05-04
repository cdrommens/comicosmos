package be.rommens.zeus.service;

import be.rommens.hera.api.Status;
import be.rommens.zeus.model.builder.ComicBuilder;
import be.rommens.zeus.model.builder.IssueBuilder;
import be.rommens.zeus.model.entity.Comic;
import be.rommens.zeus.model.entity.Issue;
import be.rommens.zeus.repository.IssueRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.cloud.stream.test.binder.MessageCollector;
import org.springframework.messaging.Message;
import org.testcontainers.shaded.com.google.common.collect.ImmutableList;

import java.time.LocalDate;
import java.util.concurrent.BlockingQueue;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;

/**
 * User : cederik
 * Date : 04/05/2020
 * Time : 21:33
 */
@SpringBootTest
public class DomainEventPublisherTest {

    @Autowired
    private Source source;
    @Autowired
    private MessageCollector messageCollector;
    @Autowired
    private IssueService issueService;
    @MockBean
    private IssueRepository issueRepository;

    private BlockingQueue<Message<?>> events;

    @BeforeEach
    public void setUp() {
        this.events = this.messageCollector.forChannel(source.output());
    }

    @Test
    public void whenDownloadNewIssues_thenDownloadIssueIsPublished() {
        Issue issue = IssueBuilder.anIssue()
            .issueId(1)
            .issueNumber("1")
            .dateOfRelease(LocalDate.of(2020,1,1))
            .downloaded(Boolean.FALSE)
            .build();
        Comic comic = ComicBuilder.aComic().comicId(1).key("comickey").status(Status.ONGOING).build();
        comic.addIssue(issue);
        BDDMockito.when(issueRepository.findAllByDownloadedFalse()).thenReturn(ImmutableList.of(issue));
        int result = issueService.downloadNewIssues();
        assertThat(result, is(1));
        assertThat(events.poll().getPayload().toString(), containsString("download-issue"));
    }
}
