package be.rommens.zeus.web;

import be.rommens.zeus.service.IssueService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

/**
 * User : cederik
 * Date : 05/05/2020
 * Time : 09:46
 */
@WebMvcTest(controllers = IssueController.class)
public class IssueControllerTest {

    @MockBean
    private IssueService issueService;

    @Test
    public void whenIssuesFound_returnNumberOfScheduledIssues() {
        //given
        given(issueService.downloadNewIssues()).willReturn(2);

        //when
        int result = issueService.downloadNewIssues();

        //then
        assertThat(result).isEqualTo(2);
    }

    @Test
    public void whenNoIssuesFound_returnZero() {
        //given
        given(issueService.downloadNewIssues()).willReturn(0);

        //when
        int result = issueService.downloadNewIssues();

        //then
        assertThat(result).isEqualTo(0);
    }
}
