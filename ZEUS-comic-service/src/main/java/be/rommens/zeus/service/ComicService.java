package be.rommens.zeus.service;

import be.rommens.zeus.model.Comic;
import be.rommens.zeus.repository.ComicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * User : cederik
 * Date : 17/04/2020
 * Time : 09:42
 */
@Service
public class ComicService {

    @Autowired
    private ComicRepository comicRepository;

    @Transactional(readOnly = true)
    public Optional<Comic> getComic(Integer id) {
        return comicRepository.findById(id);
    }

    @Transactional
    public void save(Comic comic) {
        comicRepository.save(comic);
    }
}
