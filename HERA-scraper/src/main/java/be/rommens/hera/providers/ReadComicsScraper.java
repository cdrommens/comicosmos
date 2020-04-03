package be.rommens.hera.providers;

import be.rommens.hera.Provider;
import be.rommens.hera.ProviderProperty;
import be.rommens.hera.exceptions.ComicNotFoundException;
import be.rommens.hera.models.ScrapedComic;
import be.rommens.hera.models.ScrapedIssue;
import com.google.common.collect.Iterables;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * User : cederik
 * Date : 29/03/2020
 * Time : 13:53
 */
@Component
@EnableConfigurationProperties(ProviderProperty.class)
@Slf4j
public class ReadComicsScraper {

    private final String base;
    private final ProviderProperty providerProperty;

    public ReadComicsScraper(ProviderProperty providerProperty) {
        this.providerProperty = providerProperty;
        this.base = providerProperty.getUrl().get(Provider.READCOMICS.getPropertyName());
    }

    public ScrapedComic scrapeComic(String technicalComicName) throws IOException {
        try {
            //TODO : connection details
            Document source = Jsoup.connect(buildUrlForComic(technicalComicName)).get();
            ScrapedComic scrapedComic = new ScrapedComic();

            String title =
                source.getElementsByClass("listmanga-header").stream()
                    .map(Element::text).filter(text -> !StringUtils.contains(text.trim(), "Chapter"))
                    .findFirst()
                .orElse(null);
            scrapedComic.setTitle(title);

            Elements cover = source.select("img[src*=cover]");
            if (cover != null) {
                scrapedComic.setCover(cover.attr("src"));
            }

            String type = extractValueOfNextTag(source, "dt", "Type");
            scrapedComic.setType(type);

            String dateOfRelease = extractValueOfNextTag(source, "dt", "Date of release");
            scrapedComic.setDateOfRelease(dateOfRelease);

            String status = extractValueOfNextTag(source, "dt", "Status");
            scrapedComic.setStatus(status);

            String author = extractValueOfNextTag(source, "dt", "Author(s)");
            scrapedComic.setAuthor(author);

            String summary = extractValueOfNextTag(source, "h5", "Summary");
            scrapedComic.setSummary(summary);

            Element chapters = Iterables.getOnlyElement(source.getElementsByClass("chapters"));
            Elements chapterListItem = chapters.getElementsByTag("li");
            for (Element issue : chapterListItem) {
                String key = Iterables.getOnlyElement(issue.getElementsByTag("a")).text();
                String chapterUrl = Iterables.getOnlyElement(issue.getElementsByTag("a")).attr("href");
                String value = Iterables.getOnlyElement(issue.getElementsByClass("date-chapter-title-rtl")).text();
                scrapedComic.addIssue(new ScrapedIssue(key, chapterUrl, value));
            }

            log.trace(scrapedComic.toString());

            return scrapedComic;
        }
        catch (HttpStatusException ex) {
            throw new ComicNotFoundException("URL for " + technicalComicName + " is not found", ex);
        }
    }

    public String getProviderProperty() {
        return providerProperty.getUrl().get(Provider.READCOMICS.getPropertyName());
    }

    public List<String> getAllIssues(String technicalComicName) throws IOException {
        String url = buildUrlForComic(technicalComicName);
        try {
            Document source = Jsoup.connect(url).get();
            Elements issuesHref = source.select("a[href^=" + url + "]");
            if (!issuesHref.isEmpty()) {
                //return issuesHref.stream().map(this::mapIssue).collect(Collectors.toList());
            }
        }
        catch(HttpStatusException ex) {
            //throw new ComicNotFoundException("URL for " + technicalComicName + " is not found", ex);
        }
        catch (SocketTimeoutException ex) {
            //throw new ComicNotConnectedException("Could not connect to URL for " + technicalComicName , ex);
        }
        return new ArrayList<>();
    }

    public List<String> getPageListForIssue(String technicalComicName, String issue) throws IOException {
        String url = buildUrlForIssue(technicalComicName, issue);
        try {
            Document source = Jsoup.connect(url).get();
            Elements images = source.select("img[data-src]");
            if (!images.isEmpty()) {
                return images.stream().map(element -> element.attributes().get("data-src").trim()).collect(Collectors.toList());
            }
        }
        catch(HttpStatusException ex) {
            //throw new ComicNotFoundException("URL for " + technicalComicName + " with issue " + issue + " is not found", ex);
        }
        catch (SocketTimeoutException ex) {
            //throw new ComicNotConnectedException("Could not connect to URL for " + technicalComicName + " with issue " + issue, ex);
        }
        return new ArrayList<>();
    }

    private String buildUrlForComic(String technicalComicName) {
        return base + technicalComicName;
    }

    private String buildUrlForIssue(String technicalComicName, String issue) {
        return base + technicalComicName + "/" + issue;
    }

    private String extractValueOfNextTag(Document source, String htmlTag, String searchText) {
        Elements tagList = source.getElementsContainingText(searchText);
        Element tag = tagList.stream().filter(s -> htmlTag.equals(s.tag().getName())).findFirst().orElse(null);
        if (tag != null) {
            int indexInParent = tag.parent().children().indexOf(tag);
            Element tagValue = tag.parent().child(indexInParent + 1);
            return tagValue.text();
        }
        return null;
    }
}
