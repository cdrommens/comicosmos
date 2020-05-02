package be.rommens.zeus.repository;

import be.rommens.zeus.model.Comic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * User : cederik
 * Date : 16/04/2020
 * Time : 15:58
 */
@Repository
public interface ComicRepository extends JpaRepository<Comic, Integer> {
}
