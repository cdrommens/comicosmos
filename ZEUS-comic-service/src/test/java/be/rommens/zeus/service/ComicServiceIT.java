package be.rommens.zeus.service;

import be.rommens.zeus.model.Comic;
import be.rommens.zeus.model.ComicTestObjectFactory;
import be.rommens.zeus.model.Issue;
import be.rommens.zeus.model.builder.IssueBuilder;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.junit5.api.DBRider;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.unitils.reflectionassert.ReflectionAssert;

import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * User : cederik
 * Date : 17/04/2020
 * Time : 09:46
 */
//TODO : ComiCservice enkel??
@Slf4j
@SpringBootTest(
    properties = {
        "logging.level.org.hibernate.SQL=DEBUG",
        "logging.level.org.hibernate.type.descriptor.sql.BasicBinder=TRACE",
        "spring.jpa.show-sql=true",
    }
)
@DBRider
public class ComicServiceIT {

    @Autowired
    private ComicService comicService;

    @Test
    @DataSet(value = "datasets/comicservice/setup.yml")
    @Transactional(readOnly = true)
    public void testGetComic() {
        Comic result = comicService.getComic(-1).orElse(null);
        assertThat(result, is(notNullValue()));
        assertThat(result.getIssues(), hasSize(3));
        assertThat(result.getIssues().get(2).getIssueNumber(), is("2"));
        ReflectionAssert.assertReflectionEquals(ComicTestObjectFactory.getFullDcComic(-1), result);
    }

    @Test
    @DataSet(value = "datasets/comicservice/setup.yml", transactional = true)
    @ExpectedDataSet(value = "datasets/comicservice/add-issue-to-comic-expected.yml", orderBy = "DATE_OF_RELEASE", ignoreCols = "ISSUE_ID")
    public void testAddIssueToExistingComic() {
        Comic comic = ComicTestObjectFactory.getFullDcComic(-1);
        Issue newIssue = IssueBuilder.anIssue()
            .issueNumber("4")
            .dateOfRelease(LocalDate.of(2020, 1, 1))
            .url("url4").build();
        comic.addIssue(newIssue);
        comic.setAuthor("Another author");
        comicService.save(comic);
    }

    @Test
    @DataSet(value = "datasets/comicservice/setup.yml", transactional = true)
    @ExpectedDataSet(value = "datasets/comicservice/save-new-comic-expected.yml", orderBy = "DATE_OF_RELEASE", ignoreCols = {"COMIC_ID","ISSUE_ID"})
    public void testSaveNewComic() {
        Comic comic = ComicTestObjectFactory.getFullMarvelComic(null);
        comic.getIssues().forEach(issue -> issue.setIssueId(null));
        comicService.save(comic);
    }
}
