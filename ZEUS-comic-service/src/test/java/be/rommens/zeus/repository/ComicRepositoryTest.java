package be.rommens.zeus.repository;

import be.rommens.zeus.model.Comic;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.junit5.api.DBRider;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;

/**
 * User : cederik
 * Date : 16/04/2020
 * Time : 16:03
 */
@DataJpaTest
@DBRider
public class ComicRepositoryTest {

    @Autowired
    private ComicRepository comicRepository;

    @Test
    @DataSet(value = "datasets/comicservice/setup.yml")
    public void testFindAll() {
        List<Comic> result = comicRepository.findAll();
        MatcherAssert.assertThat(result, hasSize(1));
    }
}
