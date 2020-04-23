package be.rommens.zeus.poc;

import be.rommens.zeus.model.Issue;
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

    private File IssueFolder;

    public CreateFolderCommand(AssembleIssueContext assembleIssueContext) {
        super(assembleIssueContext);
        assembleIssueContext.setIssueFolder(createIssueFolderPath(assembleIssueContext.getBaseUrl(), assembleIssueContext.getIssue()));
        this.IssueFolder = new File(assembleIssueContext.getIssueFolder());
    }

    @Override
    public boolean execute() {
        try {
            // if the parent folder doesn't exist, it will create it
            FileUtils.forceMkdir(IssueFolder);
            log.info("   [CreateFolderTask] Folder {} created", IssueFolder);
            return nextExecute();
        } catch (IOException e) {
            log.error("   [CreateFolderTask] Folder {} not created", IssueFolder);
            return false;
        }
    }

    private String createIssueFolderPath(String baseUrl, Issue issue) {
        return Paths.get(baseUrl, issue.getComic().getFolder(), issue.getFolder()).toString();
    }
}
