package be.rommens.zeus.web;

import be.rommens.zeus.model.output.DownloadIssueOutput;
import be.rommens.zeus.service.IssueService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/download")
    public ResponseEntity<DownloadIssueOutput> downloadIssues() {
        int result = issueService.downloadNewIssues();
        return new ResponseEntity<>(new DownloadIssueOutput(result), HttpStatus.OK);
    }
}
