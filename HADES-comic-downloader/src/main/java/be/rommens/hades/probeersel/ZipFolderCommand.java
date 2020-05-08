package be.rommens.hades.probeersel;

import be.rommens.hades.assembler.DownloadIssueMessage;
import be.rommens.hades.assembler.IssueAssemblyContext;
import be.rommens.hades.core.CommandResult;
import lombok.extern.slf4j.Slf4j;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.progress.ProgressMonitor;
import org.apache.commons.lang3.ArrayUtils;

import java.io.File;
import java.nio.file.Paths;

/**
 * User : cederik
 * Date : 20/04/2020
 * Time : 20:25
 */
@Slf4j
public class ZipFolderCommand extends AbstractCommand {

    private static final String EXTENSION = "cbz";

    private final File issueFolder;
    private final String cbzFilePath;

    public ZipFolderCommand(IssueAssemblyContext issueAssemblyContext) {
        super(issueAssemblyContext);
        this.issueFolder = new File(issueAssemblyContext.getIssueFolder());
        this.cbzFilePath = createCbzFilePath(issueAssemblyContext.getBaseUrl(), issueAssemblyContext.getDownloadIssueMessage());
    }

    @Override
    public CommandResult body() {
        if (ArrayUtils.isEmpty(issueFolder.listFiles())) {
            log.error(issueFolder + " is empty! No files to zip");
            return CommandResult.ERROR;
        }
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
                return CommandResult.COMPLETED;
            } else {
                return CommandResult.ERROR;
            }
        } catch (ZipException e) {
            log.error("   [CreateZip] Something went wrong ", e);
            return CommandResult.ERROR;
        } catch (InterruptedException e) {
            log.error("   [CreateZip] Interrupted! ", e);
            // Restore interrupted state...
            Thread.currentThread().interrupt();
            return CommandResult.ERROR;
        }
    }

    private String createCbzFilePath(String baseUrl, DownloadIssueMessage downloadIssueMessage) {
        return Paths.get(baseUrl, downloadIssueMessage.getComicFolder(), downloadIssueMessage.getIssueFolder() + "." + EXTENSION).toString();
    }
}
