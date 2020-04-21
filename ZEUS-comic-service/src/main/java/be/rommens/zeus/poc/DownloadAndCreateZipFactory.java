package be.rommens.zeus.poc;

import org.springframework.stereotype.Component;

import java.util.List;

/**
 * User : cederik
 * Date : 21/04/2020
 * Time : 20:29
 */
@Component
public class DownloadAndCreateZipFactory {

    //autowire basePath
    //autowire urlInputStream things
    //use as constructor param in DownloadAndCreateZip

    public DownloadAndCreateZip createAction(String folderName, List<String> pages) {  // Issue issue
        return new DownloadAndCreateZip(folderName,pages);
    }
}
