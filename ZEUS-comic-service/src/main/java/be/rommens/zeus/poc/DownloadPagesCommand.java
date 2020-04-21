package be.rommens.zeus.poc;

import lombok.extern.slf4j.Slf4j;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * User : cederik
 * Date : 20/04/2020
 * Time : 20:10
 */
@Slf4j
public class DownloadPagesCommand extends Command {

    private final List<String> pages;
    private final String completeFolder;

    public DownloadPagesCommand(DownloadAndCreateZip downloadAndCreateZip, List<String> pages, String completeFolder) {
        super(downloadAndCreateZip);
        this.pages = pages;
        this.completeFolder = completeFolder;
    }

    @Override
    public boolean execute() {
        int numberOfDownloadedPages = 0;
        for (String page : pages) {
            // path maken voor weg te schrijven file
            // url moet string blijven
            Path destinationFileName = Paths.get(page);
            Path destinationFile = Paths.get(completeFolder + "/" + destinationFileName.getFileName().toString());

            if (downloadPage(page, destinationFile)) {
                numberOfDownloadedPages++;
            }
        }
        log.info("   [DownloadPages] Downloaded {} / {} pages", numberOfDownloadedPages, pages.size());

        if (numberOfDownloadedPages != pages.size()) {
            log.error("   [DownloadPages] Not all pages downloaded");
            return false;
        }
        return nextExecute();
    }

    private Boolean downloadPage(String url, Path page){
        try {
            save(getInputStream(url), getOutputStream(page));
            return true;
        } catch (IOException e) {
            log.error("   [DownloadPages] Something went wrong ", e);
            return false;
        }
    }

    private void save(InputStream inputStream, OutputStream outputStream) throws IOException {
        byte[] b = new byte[2048];
        int length;

        while ((length = inputStream.read(b)) != -1) {
            outputStream.write(b, 0, length);
        }
    }

    InputStream getInputStream(String destinationFile) throws IOException {
        try (UrlImageInputStream imageInputStream = new UrlImageInputStream(destinationFile)) {
            imageInputStream.setSettings();
            return imageInputStream;
        }
    }

    OutputStream getOutputStream(Path destinationFile) throws FileNotFoundException {
        return new FileOutputStream(destinationFile.toFile());
    }
}
