package be.rommens.hera;

import be.rommens.hera.api.models.ScrapedComic;
import be.rommens.hera.api.models.ScrapedIssue;
import be.rommens.hera.api.models.ScrapedIssueDetails;
import be.rommens.hera.builders.ScrapedComicBuilder;
import be.rommens.hera.builders.ScrapedIssueBuilder;
import be.rommens.hera.builders.ScrapedIssueDetailsBuilder;
import be.rommens.hera.core.AbstractScraper;
import be.rommens.hera.core.ScrapingConfig;
import be.rommens.hera.dataset.Comic;
import be.rommens.hera.dataset.Issue;
import org.apache.commons.io.IOUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User : cederik
 * Date : 06/04/2020
 * Time : 08:51
 */
public class ScraperMock extends AbstractScraper {

    private final Map<String, ScrapedComic> scrapedComics = new HashMap<>();
    private final Map<String, ScrapedIssue> scrapedIssues = new HashMap<>();

    public ScraperMock(ScrapingConfig scrapingConfig, List<Comic> givenResults) {
        super(scrapingConfig);
        transformGivenResults(givenResults);
    }

    private void transformGivenResults(List<Comic> givenResults) {
        for (Comic comic : givenResults) {
            ScrapedComic scrapedComic = new ScrapedComicBuilder()
                .title(comic.getTitle())
                .cover(comic.getCover())
                .publisher(comic.getPublisher())
                .dateOfRelease(comic.getDateOfRelease())
                .status(comic.getStatus())
                .author(comic.getAuthor())
                .summary(comic.getSummary())
                .build();

            for (Issue issue : comic.getIssues()) {
                ScrapedIssueDetails scrapedIssueDetails = new ScrapedIssueDetailsBuilder()
                    .issue(issue.getIssueNumber())
                    .date(issue.getDate())
                    .url(issue.getUrl())
                    .build();
                scrapedComic.addIssue(scrapedIssueDetails);

                ScrapedIssue scrapedIssue = new ScrapedIssueBuilder()
                    .comic(comic.getKey())
                    .issueNumber(issue.getIssueNumber())
                    .numberOfPages(issue.getPages().size())
                    .addPages(issue.getPages())
                    .build();
                scrapedIssues.putIfAbsent(composeKey(comic.getKey(), issue.getIssueNumber()), scrapedIssue);
            }
            scrapedComics.putIfAbsent(comic.getKey(), scrapedComic);
        }
    }

    @Override
    protected String buildUrlForComic(String technicalComicName) {
        throw new UnsupportedOperationException("Should not be called");
    }

    @Override
    protected String buildUrlForIssue(String technicalComicName, String issue) {
        throw new UnsupportedOperationException("Should not be called");
    }

    @Override
    public ScrapedComic scrapeComic(String technicalComicName) throws IOException {
        if (scrapedComics.containsKey(technicalComicName)) {
            return scrapedComics.get(technicalComicName);
        }
        throw throwComicNotFound(technicalComicName, null);
    }

    @Override
    public ScrapedIssue scrapeIssue(String technicalComicName, String issue) throws IOException {
        String key = composeKey(technicalComicName, issue);
        if (scrapedIssues.containsKey(key)) {
            return scrapedIssues.get(key);
        }
        throw throwComicNotFound(technicalComicName, null);
    }

    @Override
    public byte[] downloadPage(String url) throws FileNotFoundException {
        InputStream inputStream = this.getClass().getResourceAsStream("/__files/" + url);
        if (inputStream == null) {
            throw throwPageNotFound(url);
        }
        try {
            return IOUtils.toByteArray(inputStream);
        } catch (IOException e) {
            throw throwPageNotFound(url);
        }
    }


    private String composeKey(String technicalComicName, String issueNumber) {
        return technicalComicName + "-" + issueNumber;
    }
}
