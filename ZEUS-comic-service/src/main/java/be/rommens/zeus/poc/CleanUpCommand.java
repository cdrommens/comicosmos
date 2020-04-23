package be.rommens.zeus.poc;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * User : cederik
 * Date : 20/04/2020
 * Time : 20:00
 */
@Slf4j
public class CleanUpCommand extends AbstractCommand {

    private final File issueFolder;

    public CleanUpCommand(AssembleIssueContext assembleIssueContext) {
        super(assembleIssueContext);
        this.issueFolder = new File(assembleIssueContext.getIssueFolder());
    }

    @Override
    public boolean execute() {
        try {
            FileUtils.deleteDirectory(issueFolder);
            if (issueFolder.exists()) {
                log.error("   [CleanUp] Something went wrong when deleting folder");
                return false;
            }
            log.info("   [CleanUp] Folder {} deleted", issueFolder);
            return nextExecute();
        } catch (IOException e) {
            log.error("   [CleanUp] Something went wrong when deleting folder; cause :", e);
            return false;
        }
    }
}
