package be.rommens.hades.assembler;

import be.rommens.hades.core.AssemblyChainFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.Queue;

/**
 * User : cederik
 * Date : 21/04/2020
 * Time : 20:13
 */
@Component
public class IssueAssembler {

    @Autowired
    private AssemblyChainFactory<Issue> issueAssemblyChainFactory;

    private Queue<Issue> workIssueQueue = new LinkedList<>();

    public void addToQueue(Issue issue) {
        workIssueQueue.add(issue);
    }

    // moet een aparte scheduled task worden
    public void scheduledTask() {
        Issue issue = workIssueQueue.poll();

        // factory aanroepen om een DownloadAndCreateZip
        issueAssemblyChainFactory.createAssemblyChain(issue).execute();
    }

}
