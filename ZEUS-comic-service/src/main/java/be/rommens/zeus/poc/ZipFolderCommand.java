package be.rommens.zeus.poc;

import be.rommens.zeus.model.Issue;
import lombok.extern.slf4j.Slf4j;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.progress.ProgressMonitor;

import java.io.File;
import java.nio.file.Paths;

/**
 * User : cederik
 * Date : 20/04/2020
 * Time : 20:25
 */
@Slf4j
public class ZipFolderCommand extends AbstractCommand {

    private static final String EXTENTION = "cbz";

    private final File issueFolder;
    private final String cbzFilePath;

    public ZipFolderCommand(AssembleIssueContext assembleIssueContext) {
        super(assembleIssueContext);
        this.issueFolder = new File(assembleIssueContext.getIssueFolder());
        this.cbzFilePath = createCbzFilePath(assembleIssueContext.getBaseUrl(), assembleIssueContext.getIssue());
    }

    @Override
    public boolean execute() {
        try {
            ZipFile zipFile = new ZipFile(cbzFilePath);
            ProgressMonitor progressMonitor = zipFile.getProgressMonitor();
            zipFile.setRunInThread(true);
            zipFile.addFolder(issueFolder);
            while (!progressMonitor.getState().equals(ProgressMonitor.State.READY)) {
                log.info("   [CreateZip] Percentage done: " + progressMonitor.getPercentDone());
                log.info("   [CreateZip] Current file: " + progressMonitor.getFileName());
                log.info("   [CreateZip] Current task: " + progressMonitor.getCurrentTask());

                Thread.sleep(100);
            }
            if (zipFile.isValidZipFile()) {
                log.info("   [CreateZip] {} is created", cbzFilePath);
                return nextExecute();
            } else {
                return false;
            }
        } catch (ZipException | InterruptedException e) {
            log.error("   [CreateZip] Something went wrong ", e);
            return false;
        }
    }

    private String createCbzFilePath(String baseUrl, Issue issue) {
        return Paths.get(baseUrl, issue.getComic().getFolder(), issue.getFolder() + "." + EXTENTION).toString();
    }
}
