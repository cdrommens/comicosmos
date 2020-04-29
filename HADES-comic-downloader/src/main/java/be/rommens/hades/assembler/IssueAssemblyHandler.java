package be.rommens.hades.assembler;

import be.rommens.hades.core.AssemblyChainFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

/**
 * User : cederik
 * Date : 28/04/2020
 * Time : 15:25
 */
@Slf4j
@EnableBinding(Sink.class)
public class IssueAssemblyHandler {

    private final AssemblyChainFactory<Issue> issueAssemblyChainFactory;

    public IssueAssemblyHandler(AssemblyChainFactory<Issue> issueAssemblyChainFactory) {
        this.issueAssemblyChainFactory = issueAssemblyChainFactory;
    }

    @StreamListener(Sink.INPUT)
    public void processIssue(Issue issue) {
        log.info("message received {} - {}", issue.getComicKey(), issue.getIssueNumber());
        issueAssemblyChainFactory.createAssemblyChain(issue).execute();
    }
}
