package be.rommens.zeus.web;

import be.rommens.zeus.model.entity.Issue;
import be.rommens.zeus.model.output.IssueToDownloadConverter;
import be.rommens.zeus.model.output.IssueToDownloadList;
import be.rommens.zeus.service.IssueService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * User : cederik
 * Date : 05/05/2020
 * Time : 09:41
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/issue")
public class IssueController {

    private final IssueService issueService;

    @GetMapping("/todownload")
    public IssueToDownloadList downloadIssues() {
        List<Issue> issues = issueService.downloadNewIssues();
        IssueToDownloadList result = new IssueToDownloadList();
        issues.stream().map(issue -> new IssueToDownloadConverter().convert(issue)).forEach(result::add);
        return result;
    }
}
