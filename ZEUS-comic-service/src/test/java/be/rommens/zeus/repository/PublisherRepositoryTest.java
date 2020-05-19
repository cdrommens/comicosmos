package be.rommens.zeus.repository;

import be.rommens.zeus.model.entity.Publisher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * User : cederik
 * Date : 09/04/2020
 * Time : 14:35
 */
@DataJpaTest
class PublisherRepositoryTest {

    @Autowired
    private PublisherRepository repository;

    @Test
    void testFindAll() {
        List<Publisher> result = repository.findAll();
        assertThat(result).hasSize(2);
    }
}
