package be.rommens.zeus.service;

import be.rommens.hera.api.Provider;
import be.rommens.hera.api.Status;
import be.rommens.zeus.model.builder.ComicBuilder;
import be.rommens.zeus.model.builder.IssueBuilder;
import be.rommens.zeus.model.entity.Comic;
import be.rommens.zeus.model.entity.Issue;
import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.configuration.Orthography;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.junit5.api.DBRider;
import com.google.common.collect.Iterables;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * User : cederik
 * Date : 04/05/2020
 * Time : 21:16
 */
@Slf4j
@SpringBootTest
@DBRider
@DBUnit(caseInsensitiveStrategy = Orthography.LOWERCASE, cacheConnection = false)
@ActiveProfiles("container")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class IssueServiceIT {

    @Autowired
    private IssueService issueService;

    @Test
    @DataSet(value = "datasets/comicservice/setup.yml")
    public void whenIssuesFound_thenListIsReturned() {
        //given
        Comic comic = ComicBuilder.aComic()
            .key("batman-2016")
            .provider(Provider.READCOMICS)
            .status(Status.ONGOING)
            .issue(IssueBuilder.anIssue()
                .issueId(-3)
                .issueNumber("Annual-1"))
                .dateOfRelease(LocalDate.of(2016, 2, 1))
            .build();

        //when
        List<Issue> result = issueService.downloadNewIssues();

        //then
        assertThat(result).hasSize(1);
        assertThat(result).contains(Iterables.getOnlyElement(comic.getIssues()));
    }

    @Test
    public void whenNoIssuesFound_thenEmptyListIsReturned() {
        //when
        List<Issue> result = issueService.downloadNewIssues();

        //then
        assertThat(result).hasSize(0);
    }
}
