package be.rommens.hermes.web;

import be.rommens.hermes.model.output.DownloadIssueOutput;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.cloud.stream.test.binder.MessageCollector;
import org.springframework.messaging.Message;
import org.springframework.test.web.servlet.MockMvc;

import java.util.concurrent.BlockingQueue;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

/**
 * User : cederik
 * Date : 13/05/2020
 * Time : 09:42
 */
@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    properties = {
        "service.zeus.hostname=http://localhost:${stubrunner.runningstubs.be.rommens.ZEUS-comic-service.port}"
    })
@AutoConfigureStubRunner(
    ids = {"be.rommens:ZEUS-comic-service:+:stubs" },
    stubsMode = StubRunnerProperties.StubsMode.LOCAL)
@AutoConfigureMockMvc
class IssueControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private Source source;

    @Autowired
    private MessageCollector messageCollector;

    private BlockingQueue<Message<?>> events;

    @BeforeEach
    void setUp() {
        this.events = this.messageCollector.forChannel(source.output());
    }

    @Test
    void whenDownloadIssuesIsCalled_thenCheckTheResponseAndMessagesPublishedOnKafka() throws Exception {
        //given
        DownloadIssueOutput expected = new DownloadIssueOutput(2);

        //when/then
        this.mockMvc.perform(get("/issue/download"))
            .andExpect(content().json(objectMapper.writeValueAsString(expected)));

        assertThat(events.size()).isEqualTo(2);
        assertThat(events.poll().getPayload().toString()).contains("\"issueNumber\":\"Annual-1\"");
        assertThat(events.poll().getPayload().toString()).contains("\"issueNumber\":\"1\"");
    }
}
