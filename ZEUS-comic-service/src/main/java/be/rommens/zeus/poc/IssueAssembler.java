package be.rommens.zeus.poc;

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

    Queue<DownloadAndCreateZip> workQueue = new LinkedList<>();

    public void addToQueue(DownloadAndCreateZip downloadAndCreateZip) {
        workQueue.add(downloadAndCreateZip);
    }

    public void assemble() {
        workQueue.poll().executeCommand();
    }
}
