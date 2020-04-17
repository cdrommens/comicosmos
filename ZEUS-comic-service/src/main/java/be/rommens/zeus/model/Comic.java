package be.rommens.zeus.model;

import be.rommens.hera.api.Status;
import lombok.ToString;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/**
 * User : cederik
 * Date : 09/04/2020
 * Time : 14:56
 */
@Entity
@Table(name = "COMIC")
@ToString
public class Comic {

    private Integer comicId;
    private String key;
    private String name;
    private Publisher publisher;
    private Status status;
    private LocalDate dateOfRelease;
    private String author;
    private String description;
    private List<Issue> issues;

    @Id
    @SequenceGenerator(name = "COMIC_ID_GENERATOR", sequenceName = "SEQ_COMIC")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "COMIC_ID_GENERATOR")
    @Column(name = "COMIC_ID", unique = true, nullable = false)
    public Integer getComicId() {
        return comicId;
    }

    public void setComicId(Integer comicId) {
        this.comicId = comicId;
    }

    @Basic
    @Column(name = "KEY", nullable = false, unique = true)
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Basic
    @Column(name = "NAME", nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToOne(targetEntity = Publisher.class)
    @JoinColumn(name = "publisherId")
    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", nullable = false)
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Basic
    @Column(name = "DATE_OF_RELEASE")
    public LocalDate getDateOfRelease() {
        return dateOfRelease;
    }

    public void setDateOfRelease(LocalDate dateOfRelease) {
        this.dateOfRelease = dateOfRelease;
    }

    @Basic
    @Column(name = "AUTHOR")
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Lob
    @Column(name = "DESCRIPTION", columnDefinition = "CLOB")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @OneToMany(targetEntity = Issue.class, mappedBy = "comic", cascade = CascadeType.ALL)
    @OrderBy(value = "DATE_OF_RELEASE")
    public List<Issue> getIssues() {
        return issues;
    }

    public void setIssues(List<Issue> issues) {
        this.issues = issues;
    }

    @Transient
    public void addIssue(Issue issue) {
        Objects.requireNonNull(status);
        if (Status.SPECIAL == status && issues.size() > 0) {
            throw new IllegalStateException("Comic has status SPECIAL and can have only 1 issue");
        }
        issue.setComic(this);
        this.issues.add(issue);
    }

    @Transient
    public void removeIssue(Issue issue) {
        issue.setComic(null);
        this.issues.remove(issue);
    }

}
