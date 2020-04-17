package be.rommens.zeus.repository;

import be.rommens.zeus.model.Comic;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * User : cederik
 * Date : 16/04/2020
 * Time : 15:58
 */
public interface ComicRepository extends JpaRepository<Comic, Integer> {
}
