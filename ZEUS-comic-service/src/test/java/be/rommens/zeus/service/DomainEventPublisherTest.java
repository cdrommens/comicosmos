package be.rommens.zeus.service;

import be.rommens.hera.api.Status;
import be.rommens.zeus.model.builder.ComicBuilder;
import be.rommens.zeus.model.builder.IssueBuilder;
import be.rommens.zeus.model.entity.Comic;
import be.rommens.zeus.model.output.DownloadIssueOutput;
import be.rommens.zeus.repository.IssueRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Iterables;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.cloud.stream.test.binder.MessageCollector;
import org.springframework.messaging.Message;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.google.common.collect.ImmutableList;

import java.time.LocalDate;
import java.util.Collections;
import java.util.concurrent.BlockingQueue;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * User : cederik
 * Date : 04/05/2020
 * Time : 21:33
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class DomainEventPublisherTest {

    @Autowired
    private Source source;
    @Autowired
    private MessageCollector messageCollector;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;
    @MockBean
    private IssueRepository issueRepository;

    private BlockingQueue<Message<?>> events;

    @BeforeEach
    public void setUp() {
        this.events = this.messageCollector.forChannel(source.output());
    }

    @Test
    public void whenDownloadNewIssues_thenDownloadIssueIsPublished() throws Exception {
        Comic comic = ComicBuilder.aComic()
            .comicId(1)
            .key("comickey")
            .status(Status.ONGOING)
            .issue(IssueBuilder.anIssue()
                .issueId(1)
                .issueNumber("1")
                .dateOfRelease(LocalDate.of(2020,1,1))
                .downloaded(Boolean.FALSE))
            .build();
        given(issueRepository.findAllByDownloadedFalse()).willReturn(ImmutableList.of(Iterables.getOnlyElement(comic.getIssues())));

        DownloadIssueOutput expected = new DownloadIssueOutput(1);

        this.mockMvc.perform(get("/issue/download"))
            .andExpect(status().isOk())
            .andExpect(content().json(mapper.writeValueAsString(expected)))
            .andDo(print());

        assertThat(events.poll().getPayload().toString(), containsString("download-issue"));
    }

    @Test
    public void whenNoIssues_thenPublisherIsNotTriggered() throws Exception {
        given(issueRepository.findAllByDownloadedFalse()).willReturn(Collections.emptyList());

        DownloadIssueOutput expected = new DownloadIssueOutput(0);

        this.mockMvc.perform(get("/issue/download"))
            .andExpect(status().isOk())
            .andExpect(content().json(mapper.writeValueAsString(expected)))
            .andDo(print());

        assertThat(events.poll(), is(nullValue()));
    }
}
