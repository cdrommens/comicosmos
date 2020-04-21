package be.rommens.zeus.poc;

import java.io.File;
import java.util.List;

/**
 * User : cederik
 * Date : 20/04/2020
 * Time : 19:52
 */
public class DownloadAndCreateZip {

    private String baseUrl = "/Users/cederik/Documents/";
    private File destinationFolder;
    private File destinationZip;
    private final List<String> pages;

    private Command command;

    public DownloadAndCreateZip(String folderName, List<String> pages) {
        this.destinationFolder = new File(baseUrl + folderName);
        this.destinationZip = new File(baseUrl + folderName + ".zip");
        this.pages = pages;
        Command create = new CreateFolderCommand(this, destinationFolder);
        Command clean = new CleanUpCommand(this, destinationFolder);
        Command pagess = new DownloadPagesCommand(this, this.pages, destinationFolder.getAbsolutePath());
        Command zip = new ZipFolderCommand(this, destinationFolder, destinationZip);

        create.linkWith(pagess).linkWith(zip).linkWith(clean);

        command = create;
    }

    public void executeCommand() {
        command.execute();
    }
}
