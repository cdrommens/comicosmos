package be.rommens.hermes.service;

import be.rommens.hermes.connectivity.ZeusConnectorService;
import be.rommens.hermes.model.output.DownloadIssueOutput;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.util.concurrent.BlockingQueue;

import static be.rommens.hermes.model.IssueToDownloadListTestObjectFactory.getEmptyIssueToDownloadList;
import static be.rommens.hermes.model.IssueToDownloadListTestObjectFactory.getIssueToDownloadListWithSingleIssue;
import static org.assertj.core.api.Assertions.assertThat;
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
@SpringBootTest
@AutoConfigureMockMvc
class DomainEventPublisherTest {

    @Autowired
    private Source source;
    @Autowired
    private MessageCollector messageCollector;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;
    @MockBean
    private ZeusConnectorService zeusConnectorService;

    private BlockingQueue<Message<?>> events;

    @BeforeEach
    void setUp() {
        this.events = this.messageCollector.forChannel(source.output());
    }

    @Test
    void whenDownloadNewIssues_thenDownloadIssueIsPublished() throws Exception {
        //given
        given(zeusConnectorService.getIssuesToDownload()).willReturn(getIssueToDownloadListWithSingleIssue());

        DownloadIssueOutput expected = new DownloadIssueOutput(1);

        //when/then
        this.mockMvc.perform(get("/issue/download"))
            .andExpect(status().isOk())
            .andExpect(content().json(mapper.writeValueAsString(expected)))
            .andDo(print());

        assertThat(events.poll().getPayload().toString()).contains("download-issue");
    }

    @Test
    void whenNoIssues_thenPublisherIsNotTriggered() throws Exception {
        //given
        given(zeusConnectorService.getIssuesToDownload()).willReturn(getEmptyIssueToDownloadList());

        DownloadIssueOutput expected = new DownloadIssueOutput(0);

        //when / then
        this.mockMvc.perform(get("/issue/download"))
            .andExpect(status().isOk())
            .andExpect(content().json(mapper.writeValueAsString(expected)))
            .andDo(print());

        assertThat(events.poll()).isNull();
    }
}
