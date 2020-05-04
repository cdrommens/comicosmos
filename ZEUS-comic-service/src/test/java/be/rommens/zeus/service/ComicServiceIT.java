package be.rommens.zeus.service;

import be.rommens.zeus.model.ComicTestObjectFactory;
import be.rommens.zeus.model.builder.IssueBuilder;
import be.rommens.zeus.model.entity.Comic;
import be.rommens.zeus.model.entity.Issue;
import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.configuration.Orthography;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.junit5.api.DBRider;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.unitils.reflectionassert.ReflectionAssert;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * User : cederik
 * Date : 17/04/2020
 * Time : 09:46
 */
@Slf4j
@SpringBootTest
@DBRider
@DBUnit(caseInsensitiveStrategy = Orthography.LOWERCASE, cacheConnection = false)
@ActiveProfiles("container")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataSet(value = "datasets/comicservice/setup.yml")
public class ComicServiceIT {

    @Autowired
    private ComicService comicService;

    @Test
    @Transactional(readOnly = true)
    public void whenGetComicById_thenReturnComic() {
        Comic result = comicService.getComic(-1).orElse(null);
        assertThat(result, is(notNullValue()));
        assertThat(result.getIssues(), hasSize(3));
        assertThat(result.getIssues().get(2).getIssueNumber(), is("2"));
        ReflectionAssert.assertReflectionEquals(ComicTestObjectFactory.getFullDcComic(-1), result);
    }

    @Test
    @ExpectedDataSet(value = "datasets/comicservice/add-issue-to-comic-expected.yml", orderBy = "DATE_OF_RELEASE", ignoreCols = "ISSUE_ID")
    public void whenAddIssueToExistingComic_thenComicMustBeUpdated() {
        Comic comic = ComicTestObjectFactory.getFullDcComic(-1);
        Issue newIssue = IssueBuilder.anIssue()
            .issueNumber("4")
            .dateOfRelease(LocalDate.of(2020, 1, 1))
            .downloaded(false).build();
        comic.addIssue(newIssue);
        comic.setAuthor("Another author");
        comicService.save(comic);
    }

    @Test
    @ExpectedDataSet(value = "datasets/comicservice/save-new-comic-expected.yml", orderBy = "DATE_OF_RELEASE", ignoreCols = {"COMIC_ID","ISSUE_ID"})
    public void whenSaveNewComic_thenComicMustBeSaved() {
        Comic comic = ComicTestObjectFactory.getFullMarvelComic(null);
        comic.getIssues().forEach(issue -> issue.setIssueId(null));
        comicService.save(comic);
    }

    @Test
    @Transactional(readOnly = true)
    public void whenGetAllComics_thenReturnAllComics() {
        List<Comic> result = comicService.getAllComics();
        result.forEach(c -> log.info(c.toString()));
        assertThat(result, is(notNullValue()));
        assertThat(result, hasSize(1));
    }
}
