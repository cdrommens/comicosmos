package be.rommens.zeus.model.builder;

import be.rommens.hera.api.Provider;
import be.rommens.hera.api.Status;
import be.rommens.zeus.model.entity.Comic;
import be.rommens.zeus.model.entity.Issue;
import be.rommens.zeus.model.entity.Publisher;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * User : cederik
 * Date : 17/04/2020
 * Time : 10:54
 */
public final class ComicBuilder {

    private Integer comicId;
    private Provider provider;
    private String key;
    private String name;
    private Publisher publisher;
    private Status status;
    private LocalDate dateOfRelease;
    private String author;
    private String description;
    private List<Issue> issues = new ArrayList<>();

    private ComicBuilder() {
    }

    public static ComicBuilder aComic() {
        return new ComicBuilder();
    }

    public ComicBuilder comicId(Integer comicId) {
        this.comicId = comicId;
        return this;
    }

    public ComicBuilder provider(Provider provider) {
        this.provider = provider;
        return this;
    }

    public ComicBuilder key(String key) {
        this.key = key;
        return this;
    }

    public ComicBuilder name(String name) {
        this.name = name;
        return this;
    }

    public ComicBuilder publisher(Publisher publisher) {
        this.publisher = publisher;
        return this;
    }

    public ComicBuilder status(Status status) {
        this.status = status;
        return this;
    }

    public ComicBuilder dateOfRelease(LocalDate dateOfRelease) {
        this.dateOfRelease = dateOfRelease;
        return this;
    }

    public ComicBuilder author(String author) {
        this.author = author;
        return this;
    }

    public ComicBuilder description(String description) {
        this.description = description;
        return this;
    }

    public ComicBuilder issue(IssueBuilder issueBuilder) {
        this.issues.add(issueBuilder.build());
        return this;
    }

    public Comic build() {
        Comic comic = new Comic();
        comic.setComicId(comicId);
        comic.setProvider(provider);
        comic.setKey(key);
        comic.setName(name);
        comic.setPublisher(publisher);
        comic.setStatus(status);
        comic.setDateOfRelease(dateOfRelease);
        comic.setAuthor(author);
        comic.setDescription(description);
        comic.setIssues(new ArrayList<>());
        issues.forEach(comic::addIssue);
        return comic;
    }
}
