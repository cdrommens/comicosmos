package be.rommens.zeus.repository;

import be.rommens.zeus.model.entity.Comic;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.junit5.api.DBRider;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;

/**
 * User : cederik
 * Date : 16/04/2020
 * Time : 16:03
 */
@DataJpaTest(
    properties = "spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=false;MODE=PostgreSQL"
)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DBRider
@DataSet(value = "datasets/comicservice/setup.yml")
class ComicRepositoryTest {

    @Autowired
    private ComicRepository comicRepository;

    @Test
    void testFindAll() {
        List<Comic> result = comicRepository.findAll();
        MatcherAssert.assertThat(result, hasSize(1));
    }
}
