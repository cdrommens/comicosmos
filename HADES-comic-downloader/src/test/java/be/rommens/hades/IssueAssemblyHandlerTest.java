package be.rommens.hades;

import be.rommens.hades.assembler.Issue;
import be.rommens.hades.command.MockCommand;
import be.rommens.hades.core.AssemblyChainFactory;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.cloud.stream.test.binder.MessageCollector;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;

import static org.mockito.ArgumentMatchers.any;

/**
 * User : cederik
 * Date : 28/04/2020
 * Time : 15:32
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IssueAssemblyHandlerTest {

    @Autowired
    private Sink sink;

    @Autowired
    private MessageCollector messageCollector;

    @MockBean
    private AssemblyChainFactory<Issue> issueAssemblyChainFactory;

    @Test
    public void testWiring() {
        Issue issue = new Issue();
        issue.setComicKey("comickey");
        issue.setIssueNumber("1");
        Message<Issue> message = new GenericMessage<>(issue);

        BDDMockito.given(issueAssemblyChainFactory.createAssemblyChain(any())).willReturn(new MockCommand());

        sink.input().send(message);
    }
}
