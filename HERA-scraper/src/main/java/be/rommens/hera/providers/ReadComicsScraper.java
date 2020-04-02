package be.rommens.hera.providers;

import be.rommens.hera.Provider;
import be.rommens.hera.ProviderProperty;
import be.rommens.hera.exceptions.ComicNotFoundException;
import be.rommens.hera.models.ScrapedComic;
import be.rommens.hera.models.ScrapedIssue;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
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
            Document source = Jsoup.connect(buildUrlForComic(technicalComicName)).get();
            ScrapedComic scrapedComic = new ScrapedComic();
            Elements colSm8 = source.getElementsByClass("col-sm-8");
            String title =
                source.getElementsByClass("listmanga-header").stream()
                    .map(Element::text).filter(text -> !StringUtils.contains(text.trim(), "Chapter"))
                    .findFirst()
                .orElse("");
            scrapedComic.setTitle(title);
            Elements cover = source.select("img[src*=cover]");
            scrapedComic.setCover(cover.attr("src"));
            for(Element e : colSm8) {
                Elements dl = e.getElementsByTag("dl");
                for (Element d : dl) {
                    List<Node> nodes = d.childNodes();
                    List<Element> elements = nodes.stream().filter(Element.class::isInstance).map(Element.class::cast).collect(Collectors.toList());
                    String key = null;
                    String value = null;
                    for (Element s : elements) {
                        if ("dt".equals(s.tag().getName())) {
                            key = s.text();
                        }
                        if ("dd".equals(s.tag().getName())) {
                            switch (key) {
                                case "Type":
                                    value = s.text();
                                    scrapedComic.setType(value);
                                    break;
                                case "Status":
                                    value = s.getElementsByTag("span").text();
                                    scrapedComic.setStatus(value);
                                    break;
                                case "Author(s)":
                                    value = s.getElementsByTag("a").text();
                                    scrapedComic.setAuthor(value);
                                    break;
                                case "Date of release":
                                    value = s.text();
                                    scrapedComic.setDateOfRelease(value);
                                    break;
                            }
                        }
                        if (StringUtils.isNotEmpty(value)) {
                            key = null;
                            value = null;
                        }
                    }
                }
            }
            Elements mangaWell = source.getElementsByClass("manga well");
            for (Element m : mangaWell) {
                String key = null;
                String value = null;
                for (Element c : m.children()) {
                    if ("h5".equals(c.tagName())) {
                        key = c.getElementsByTag("strong").text();
                    }
                    if (StringUtils.isNotEmpty(key) && "p".equals(c.tagName())) {
                        value = c.text();
                    }
                }
                scrapedComic.setSummary(value);
            }

            Elements ul = source.getElementsByClass("chapters");
            for (Element l : ul) {
                Elements lis = l.getElementsByTag("li");
                for (Element li : lis) {
                    String key = li.getElementsByTag("a").get(0).text().trim();
                    String chapterUrl = li.getElementsByTag("a").get(0).attr("href");
                    String value = li.getElementsByClass("date-chapter-title-rtl").get(0).text().trim();
                    scrapedComic.addIssue(new ScrapedIssue(key, chapterUrl, value));
                }
            }

            log.info(scrapedComic.toString());

            return scrapedComic;
        }
        catch(HttpStatusException ex) {
            throw new ComicNotFoundException("URL for " + technicalComicName + " is not found", ex);
        }
        /*catch (SocketTimeoutException ex) {
            throw new ComicNotConnectedException("Could not connect to URL for " + technicalComicName , ex);
        }*/
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
}
