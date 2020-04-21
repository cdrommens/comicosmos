package be.rommens.zeus.poc;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * User : cederik
 * Date : 20/04/2020
 * Time : 20:25
 */
@Slf4j
public class ZipFolderCommand extends Command {

    private final File folder;
    private final File cbzFile;

    public ZipFolderCommand(DownloadAndCreateZip downloadAndCreateZip, File folder, File cbzFile) {
        super(downloadAndCreateZip);
        this.folder = folder;
        this.cbzFile = cbzFile;
    }

    @Override
    public boolean execute() {
        if (!zipFolder()) {
            return false;
        }
        log.info("   [CreateZip] {} is created", cbzFile);
        return nextExecute();
    }

    private Boolean zipFolder() {
        try (FileOutputStream fos = getOutputStream(cbzFile)) {
            ZipOutputStream zipOut = new ZipOutputStream(fos);
            zipFolder(zipOut, folder);
            if(!cbzFile.exists()) {
                log.error("   [CreateZip] CBZ file is not created");
                return false;
            } else {
                return true;
            }
        } catch (IOException e) {
            log.error("   [CreateZip] Something went wrong ", e);
            return false;
        }
    }

    private void zipFile(InputStream fis, String fileName, ZipOutputStream zipOut) throws IOException {
        ZipEntry zipEntry = new ZipEntry(fileName);
        zipOut.putNextEntry(zipEntry);
        byte[] bytes = new byte[1024];
        int length;
        while ((length = fis.read(bytes)) >= 0) {
            zipOut.write(bytes, 0, length);
        }
    }

    private void zipFolder(ZipOutputStream zipOut, File folder) throws IOException {
        if (folder.isDirectory()) {
            File[] children = folder.listFiles();
            if (children != null) {
                for (File childFile : children) {
                    zipFile(getInputStream(childFile), childFile.getName(), zipOut);
                }
            }
        }
    }

    private InputStream getInputStream(File childFile) throws FileNotFoundException {
        return new FileInputStream(childFile);
    }

    private FileOutputStream getOutputStream(File file) throws FileNotFoundException {
        return new FileOutputStream(file);
    }

}
