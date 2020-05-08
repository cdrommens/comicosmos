package be.rommens.hades;

import be.rommens.hades.assembler.IssueAssemblyChain;
import be.rommens.hades.connectivity.DownloadIssueMessage;
import be.rommens.hades.core.AssemblyChainFactory;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

/**
 * User : cederik
 * Date : 28/04/2020
 * Time : 15:32
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class IssueAssemblyListenerTest {

    @Autowired
    private Sink sink;

    @MockBean
    private AssemblyChainFactory<DownloadIssueMessage> issueAssemblyChainFactory;

    @Test
    public void testWiring() {
        //given
        DownloadIssueMessage downloadIssueMessage = new DownloadIssueMessage();
        downloadIssueMessage.setComicKey("comickey");
        downloadIssueMessage.setIssueNumber("1");
        Message<DownloadIssueMessage> message = new GenericMessage<>(downloadIssueMessage);

        IssueAssemblyChain mock = Mockito.mock(IssueAssemblyChain.class);
        given(mock.execute()).willReturn(Boolean.TRUE);
        given(issueAssemblyChainFactory.createAssemblyChain(downloadIssueMessage)).willReturn(mock);

        //when
        sink.input().send(message);

        //then
        then(mock).should(times(1)).execute();
    }
}
