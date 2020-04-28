package be.rommens.hades.command;

import be.rommens.hades.assembler.IssueAssemblyContext;
import be.rommens.hades.core.CommandResult;
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

    public CleanUpCommand(IssueAssemblyContext issueAssemblyContext) {
        super(issueAssemblyContext);
        this.issueFolder = new File(issueAssemblyContext.getIssueFolder());
    }

    @Override
    public CommandResult body() {
        try {
            FileUtils.deleteDirectory(issueFolder);
            if (issueFolder.exists()) {
                log.error("   [CleanUp] Something went wrong when deleting folder");
                return CommandResult.ERROR;
            }
            log.info("   [CleanUp] Folder {} deleted", issueFolder);
            return CommandResult.COMPLETED;
        } catch (IOException e) {
            log.error("   [CleanUp] Something went wrong when deleting folder; cause :", e);
            return CommandResult.ERROR;
        }
    }
}
