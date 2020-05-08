package be.rommens.zeus.repository;

import be.rommens.zeus.model.entity.Issue;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.junit5.api.DBRider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * User : cederik
 * Date : 02/05/2020
 * Time : 14:39
 */
@DataJpaTest(
    properties = "spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=false;MODE=PostgreSQL"
)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DBRider
@DataSet(value = "datasets/comicservice/setup.yml")
public class IssueRepositoryTest {

    @Autowired
    private IssueRepository issueRepository;

    @Test
    public void findAllIssuesToDownloaded() {
        List<Issue> result = issueRepository.findAllByDownloadedFalse();
        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
    }
}