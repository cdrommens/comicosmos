package be.rommens.zeus.repository;

import be.rommens.zeus.model.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * User : cederik
 * Date : 09/04/2020
 * Time : 14:26
 *
 * see : https://reflectoring.io/spring-boot-data-jpa-test/
 */
public interface PublisherRepository extends JpaRepository<Publisher, Integer> {
}
