package be.rommens.zeus.model;

import lombok.ToString;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.LocalDate;

/**
 * User : cederik
 * Date : 09/04/2020
 * Time : 15:03
 */
@Entity
@Table(name = "ISSUE")
@ToString
public class Issue {

    private Integer issueId;
    @ToString.Exclude
    private Comic comic;
    private String issueNumber;
    private LocalDate dateOfRelease;
    private Boolean downloaded;

    @Id
    @SequenceGenerator(name = "ISSUE_ID_GENERATOR", sequenceName = "SEQ_ISSUE")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ISSUE_ID_GENERATOR")
    @Column(name = "ISSUE_ID", unique = true, nullable = false)
    public Integer getIssueId() {
        return issueId;
    }

    public void setIssueId(Integer issueId) {
        this.issueId = issueId;
    }

    @ManyToOne(targetEntity = Comic.class)
    @JoinColumn(name = "COMIC_ID", referencedColumnName = "COMIC_ID", nullable = false)
    public Comic getComic() {
        return comic;
    }

    public void setComic(Comic comic) {
        this.comic = comic;
    }

    @Basic
    @Column(name = "ISSUE_NUMBER", nullable = false)
    public String getIssueNumber() {
        return issueNumber;
    }

    public void setIssueNumber(String issueNumber) {
        this.issueNumber = issueNumber;
    }

    @Basic
    @Column(name = "DATE_OF_RELEASE", nullable = false)
    public LocalDate getDateOfRelease() {
        return dateOfRelease;
    }

    public void setDateOfRelease(LocalDate dateOfRelease) {
        this.dateOfRelease = dateOfRelease;
    }

    @Basic
    @Column(name = "DOWNLOADED", nullable = false)
    public Boolean getDownloaded() {
        return downloaded;
    }

    public void setDownloaded(Boolean downloaded) {
        this.downloaded = downloaded;
    }
}
