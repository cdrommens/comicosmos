package be.rommens.zeus.model;

import be.rommens.hera.api.Provider;
import be.rommens.hera.api.Status;
import be.rommens.zeus.model.builder.ComicBuilder;
import be.rommens.zeus.model.builder.IssueBuilder;

import java.time.LocalDate;

/**
 * User : cederik
 * Date : 17/04/2020
 * Time : 10:51
 */
public class ComicTestObjectFactory {

    public static Comic getFullDcComic(Integer id) {
        return ComicBuilder.aComic()
            .comicId(id)
            .provider(Provider.READCOMICS)
            .key("batman-2016")
            .name("Batman (2016-)")
            .publisher(PublisherTestObjectFactory.getDcComicsPublisher())
            .status(Status.ONGOING)
            .dateOfRelease(LocalDate.of(2016,1,1))
            .author("Tom King")
            .description("\"I AM GOTHAM\" Chapter One No one has ever stopped the Caped Crusader. Not The Joker. Not Two-Face. Not even the entire Justice League. But how does Batman confront a new hero who wants to save the city from the Dark Knight? CAN'T MISS: Superstar artist David Finch returns to Batman alongside writer Tom King for this five-part storyline.")
            .issue(IssueBuilder.anIssue()
                .issueId(-1)
                .issueNumber("1")
                .dateOfRelease(LocalDate.of(2016,1,1))
                .url("url1"))
            .issue(IssueBuilder.anIssue()
                .issueId(-3)
                .issueNumber("Annual-1")
                .dateOfRelease(LocalDate.of(2016,2,1))
                .url("url-annual1"))
            .issue(IssueBuilder.anIssue()
                .issueId(-2)
                .issueNumber("2")
                .dateOfRelease(LocalDate.of(2016,3,1))
                .url("url2"))
            .build();
    }

    public static Comic getFullMarvelComic(Integer id) {
        return ComicBuilder.aComic()
            .comicId(id)
            .provider(Provider.READCOMICS)
            .key("spiderman-2016")
            .name("Spider-Man (2016-)")
            .publisher(PublisherTestObjectFactory.getMarvelComicsPublisher())
            .status(Status.ONGOING)
            .dateOfRelease(LocalDate.of(2016,1,1))
            .author("Brian Michael Bendis")
            .description("Miles Morales has been doing the super hero thing for a while, now, but after SECRET WARS, he’ll be a full-fledged member of the Marvel Universe. Swinging next to The Invincible Iron Man, The Mighty Thor and the All-New Captain America as a card-carrying member of the Avengers is an adventure, but it’s not all fun and games for New York City’s main Spider-MAN!")
                .issue(IssueBuilder.anIssue()
                .issueId(-1)
                .issueNumber("1")
                .dateOfRelease(LocalDate.of(2016,12,11))
                .url("urlspider"))
            .build();
    }
}
