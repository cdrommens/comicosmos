package be.rommens.zeus.poc;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * User : cederik
 * Date : 20/04/2020
 * Time : 20:10
 */
@Slf4j
public class DownloadIssuePagesCommand extends AbstractCommand {

    private final String issueFolder;
    private int numberOfDownloadedPages = 0;

    public DownloadIssuePagesCommand(AssembleIssueContext assembleIssueContext) {
        super(assembleIssueContext);
        this.issueFolder = assembleIssueContext.getIssueFolder();
    }

    @Override
    public boolean execute() {
        try {
            for (String page : assembleIssueContext.getScrapedIssue().getPages()) {
                //TODO : https://stackoverflow.com/questions/37410249/wiremock-to-serve-images-stored-on-local-disk
                downloadFile(page);
            }
        } catch (IOException e) {
            log.error("   [DownloadPages] Something went wrong ", e);
            return false;
        }
        if (areAllPagesDownloaded()) {
            log.error("   [DownloadPages] Not all pages downloaded");
            return false;
        }
        return nextExecute();
    }

    private boolean areAllPagesDownloaded() {
        return numberOfDownloadedPages != assembleIssueContext.getScrapedIssue().getNumberOfPages();
    }

    //TODO : move to scraper (with header and some delay)
    private void downloadFile(String page) throws IOException {
        FileUtils.copyURLToFile(new URL(page), getDestinationFile(page));
        //TODO : check filesize
        numberOfDownloadedPages++;
        log.info("   [DownloadPages] Downloaded {} / {} pages", numberOfDownloadedPages, assembleIssueContext.getScrapedIssue().getNumberOfPages());
    }

    private File getDestinationFile(String page) {
        Path destinationFileName = Paths.get(page);
        return Paths.get(issueFolder, destinationFileName.getFileName().toString()).toFile();
    }
}
