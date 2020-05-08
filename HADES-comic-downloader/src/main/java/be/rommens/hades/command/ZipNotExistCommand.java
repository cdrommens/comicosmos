package be.rommens.hades.command;

import be.rommens.hades.assembler.IssueAssemblyContext;
import be.rommens.hades.connectivity.DownloadIssueMessage;
import be.rommens.hades.core.AbstractCommand;
import be.rommens.hades.core.CommandResult;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * User : cederik
 * Date : 07/05/2020
 * Time : 14:02
 */
//TODO : to much duplication
@Slf4j
public class ZipNotExistCommand extends AbstractCommand {

    private static final String EXTENSION = "cbz";
    private final String cbzFilePath;

    public ZipNotExistCommand(IssueAssemblyContext issueAssemblyContext) {
        super(issueAssemblyContext);
        this.cbzFilePath = createCbzFilePath(issueAssemblyContext.getBaseUrl(), issueAssemblyContext.getDownloadIssueMessage());
    }

    @Override
    public CommandResult body() {
        if (Files.exists(Paths.get(cbzFilePath))) {
            return CommandResult.ERROR;
        }
        return CommandResult.COMPLETED;
    }

    @Override
    public boolean rollback() {
        log.info("ZipNotExistCommand rolled back");
        return false;
    }

    private String createCbzFilePath(String baseUrl, DownloadIssueMessage downloadIssueMessage) {
        return Paths.get(baseUrl, downloadIssueMessage.getComicFolder(), downloadIssueMessage.getIssueFolder() + "." + EXTENSION).toString();
    }
}
