package be.rommens.zeus.poc;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.stream.Stream;

/**
 * User : cederik
 * Date : 20/04/2020
 * Time : 20:00
 */
@Slf4j
public class CleanUpCommand extends Command {

    private final File completeFolder;

    public CleanUpCommand(DownloadAndCreateZip downloadAndCreateZip, File completeFolder) {
        super(downloadAndCreateZip);
        this.completeFolder = completeFolder;
    }

    @Override
    public boolean execute() {
        try (Stream<Path> paths = Files.walk(completeFolder.toPath())) {
            paths.sorted(Comparator.reverseOrder()).map(Path::toFile).forEach(File::delete);
            if (completeFolder.exists()) {
                log.error("   [CleanUp] Something went wrong when deleting folder");
                return false;
            }
            log.info("   [CleanUp] Folder {} deleted", completeFolder);
            return nextExecute();
        } catch (IOException e) {
            log.error("   [CleanUp] Something went wrong when deleting folder; cause :", e);
            return false;
        }
    }
}
