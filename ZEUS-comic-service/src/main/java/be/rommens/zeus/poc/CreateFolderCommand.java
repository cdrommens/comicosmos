package be.rommens.zeus.poc;

import lombok.extern.slf4j.Slf4j;

import java.io.File;

/**
 * User : cederik
 * Date : 20/04/2020
 * Time : 19:56
 */
@Slf4j
public class CreateFolderCommand extends Command {

    private final File completeFolder;

    public CreateFolderCommand(DownloadAndCreateZip downloadAndCreateZip, File completeFolder) {
        super(downloadAndCreateZip);
        this.completeFolder = completeFolder;
    }

    @Override
    public boolean execute() {
        if (!completeFolder.exists()){
            if (completeFolder.mkdirs()) {
                log.info("   [CreateFolderTask] Folder {} created", completeFolder);
                return nextExecute();
            }
            log.error("   [CreateFolderTask] Folder {} not created", completeFolder);
            return false;
        }
        return nextExecute();
    }
}
