package be.rommens.zeus.service;

import be.rommens.zeus.model.entity.Comic;
import be.rommens.zeus.model.entity.Issue;
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
public class ComicServiceImpl implements ComicService {

    private final ComicRepository comicRepository;

    //TODO : resttemplate call
    /*
    @Autowired
    private IssueAssembler issueAssembler;
     */

    @Override
    @Transactional(readOnly = true)
    public Optional<Comic> getComic(Integer id) {
        return comicRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Comic> getAllComics() {
        return comicRepository.findAll();
    }

    @Override
    @Transactional
    public void save(Comic comic) {
        comicRepository.save(comic);
    }

    @Override
    public void checkForNewIssues() {
        // via repo haal de ongedownloade issues op
        // for loop
        Issue issue1 = new Issue();
        Issue issue2 = new Issue();
        // return 2 issues scheduled
    }
}
