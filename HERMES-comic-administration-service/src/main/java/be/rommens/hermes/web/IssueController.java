package be.rommens.hermes.web;

import be.rommens.hermes.model.output.DownloadIssueOutput;
import be.rommens.hermes.service.IssueService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * User : cederik
 * Date : 12/05/2020
 * Time : 14:52
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/issue")
public class IssueController {

    private final IssueService issueService;

    @GetMapping("/download")
    public DownloadIssueOutput downloadIssues() {
        int result = issueService.downloadNewIssues();
        return new DownloadIssueOutput(result);
    }

    @GetMapping("/download/comic/{name}/issue/{issue}")
    public DownloadIssueOutput downloadSingleIssue(@PathVariable("name") String name, @PathVariable("issue") String issue) {
        int result = issueService.downloadSignleIssue(name, issue);
        return new DownloadIssueOutput(result);
    }
}
