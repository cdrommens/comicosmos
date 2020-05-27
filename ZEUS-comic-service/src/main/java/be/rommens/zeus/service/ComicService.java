package be.rommens.zeus.service;

import be.rommens.zeus.model.entity.Comic;

import java.util.List;
import java.util.Optional;

/**
 * User : cederik
 * Date : 27/05/2020
 * Time : 13:53
 */
public interface ComicService {

    Optional<Comic> getComic(Integer id);

    List<Comic> getAllComics();

    void save(Comic comic);

    void checkForNewIssues();
}
