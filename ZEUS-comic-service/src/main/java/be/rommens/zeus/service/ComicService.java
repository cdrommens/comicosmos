package be.rommens.zeus.service;

import be.rommens.zeus.model.Comic;
import be.rommens.zeus.model.Issue;
import be.rommens.zeus.poc.IssueAssembler;
import be.rommens.zeus.repository.ComicRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
public class ComicService {

    @Autowired
    private ComicRepository comicRepository;

    @Autowired
    private IssueAssembler issueAssembler;

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

    public void CheckForNewIssues() {
        // via repo haal de ongedownloade issues op
        // for loop
        Issue issue1 = new Issue();
        Issue issue2 = new Issue();
        issueAssembler.addToQueue(issue1);
        issueAssembler.addToQueue(issue2);
        // return 2 issues scheduled

    }
}
