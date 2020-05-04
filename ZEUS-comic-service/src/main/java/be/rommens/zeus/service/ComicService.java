package be.rommens.zeus.service;

import be.rommens.zeus.model.Comic;
import be.rommens.zeus.model.Issue;
import be.rommens.zeus.repository.ComicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * User : cederik
 * Date : 17/04/2020
 * Time : 09:42
 */
@Service
@RequiredArgsConstructor
public class ComicService {

    private final ComicRepository comicRepository;

    //TODO : resttemplate call
    /*
    @Autowired
    private IssueAssembler issueAssembler;
     */

    @Transactional(readOnly = true)
    public Optional<Comic> getComic(Integer id) {
        return comicRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Comic> getAllComics() {
        return comicRepository.findAll();
    }

    @Transactional
    public void save(Comic comic) {
        comicRepository.save(comic);
    }

    public void checkForNewIssues() {
        // via repo haal de ongedownloade issues op
        // for loop
        Issue issue1 = new Issue();
        Issue issue2 = new Issue();
        // return 2 issues scheduled
    }
}
