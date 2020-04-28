package be.rommens.hades.command;

import be.rommens.hades.assembler.Issue;
import be.rommens.hades.assembler.IssueAssemblyContext;
import be.rommens.hades.core.CommandResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

/**
 * User : cederik
 * Date : 20/04/2020
 * Time : 19:56
 */
@Slf4j
public class CreateFolderCommand extends AbstractCommand {

    private File issueFolder;

    public CreateFolderCommand(IssueAssemblyContext issueAssemblyContext) {
        super(issueAssemblyContext);
        issueAssemblyContext.setIssueFolder(createIssueFolderPath(issueAssemblyContext.getBaseUrl(), issueAssemblyContext.getIssue()));
        this.issueFolder = new File(issueAssemblyContext.getIssueFolder());
    }

    @Override
    public CommandResult body() {
        try {
            // if the parent folder doesn't exist, it will create it
            FileUtils.forceMkdir(issueFolder);
            log.info("   [CreateFolderTask] Folder {} created", issueFolder);
            return CommandResult.COMPLETED;
        } catch (IOException e) {
            log.error("   [CreateFolderTask] Folder {} not created", issueFolder);
            return CommandResult.ERROR;
        }
    }

    private String createIssueFolderPath(String baseUrl, Issue issue) {
        return Paths.get(baseUrl, issue.getComicFolder(), issue.getIssueFolder()).toString();
    }
}
