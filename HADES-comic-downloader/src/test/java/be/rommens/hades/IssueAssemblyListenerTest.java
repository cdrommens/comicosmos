package be.rommens.hades;

import be.rommens.hades.assembler.DownloadIssueMessage;
import be.rommens.hades.command.MockCommand;
import be.rommens.hades.core.AssemblyChainFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.cloud.stream.test.binder.MessageCollector;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

/**
 * User : cederik
 * Date : 28/04/2020
 * Time : 15:32
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IssueAssemblyListenerTest {

    @Autowired
    private Sink sink;

    @Autowired
    private MessageCollector messageCollector;

    @MockBean
    private AssemblyChainFactory<DownloadIssueMessage> issueAssemblyChainFactory;

    @Test
    public void testWiring() {
        DownloadIssueMessage downloadIssueMessage = new DownloadIssueMessage();
        downloadIssueMessage.setComicKey("comickey");
        downloadIssueMessage.setIssueNumber("1");
        Message<DownloadIssueMessage> message = new GenericMessage<>(downloadIssueMessage);

        given(issueAssemblyChainFactory.createAssemblyChain(any())).willReturn(new MockCommand());

        sink.input().send(message);
    }
}
