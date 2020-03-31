package be.rommens.hera.providers;

import be.rommens.hera.ProviderProperty;
import be.rommens.hera.Provider;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
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
public class ReadComicsScraper {

    private final String base;
    private final ProviderProperty providerProperty;

    public ReadComicsScraper(ProviderProperty providerProperty) {
        this.providerProperty = providerProperty;
        this.base = providerProperty.getUrl().get(Provider.READCOMICS.getPropertyName());
    }

    public String testConn() throws IOException {
        String url = "http://localhost:8888/some/thing";
        try {
            Document source = Jsoup.connect(url).get();
            return source.body().toString();
        }
        catch(HttpStatusException ex) {
            //throw new ComicNotFoundException("URL for " + technicalComicName + " is not found", ex);
        }
        catch (SocketTimeoutException ex) {
            //throw new ComicNotConnectedException("Could not connect to URL for " + technicalComicName , ex);
        }
        return "";
    }

    public String getProviderProperty() {
        return providerProperty.getUrl().get(Provider.READCOMICS.getPropertyName());
    }

    public List<String> getAllIssues(String technicalComicName) throws IOException {
        String url = buildUrlForIssueOverview(technicalComicName);
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

    private String buildUrlForIssueOverview(String technicalComicName) {
        return base + technicalComicName;
    }

    private String buildUrlForIssue(String technicalComicName, String issue) {
        return base + technicalComicName + "/" + issue;
    }
}
