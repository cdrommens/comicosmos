package be.rommens.zeus.repository;

import be.rommens.zeus.model.entity.Issue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * User : cederik
 * Date : 02/05/2020
 * Time : 14:33
 */
@Repository
public interface IssueRepository extends JpaRepository<Issue, Integer> {

    //TODO : custom query met join voor performantie
    List<Issue> findAllByDownloadedFalse();

}
