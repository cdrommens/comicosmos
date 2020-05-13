package be.rommens.hermes.web;

import be.rommens.hermes.model.output.DownloadIssueOutput;
import be.rommens.hermes.service.IssueService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * User : cederik
 * Date : 13/05/2020
 * Time : 09:43
 */
@WebMvcTest(controllers = IssueController.class)
public class IssueControllerTest {

    private static final String DOWNLOAD_URL = "/issue/download";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private IssueService issueService;

    @Test
    public void whenServiceReturns2_thenNumberOfIssuesScheduledNotEmpty() throws Exception {
        //given
        given(issueService.downloadNewIssues()).willReturn(2);
        DownloadIssueOutput expected = new DownloadIssueOutput(2);
        //when/then
        this.mockMvc.perform(get(DOWNLOAD_URL))
            .andDo(print())
            .andExpect(status().is2xxSuccessful())
            .andExpect(content().json(objectMapper.writeValueAsString(expected)));
    }

    @Test
    public void whenServiceReturnsEmpty_thenNumberOfIssuesScheduledZero() throws Exception {
        //given
        given(issueService.downloadNewIssues()).willReturn(0);
        DownloadIssueOutput expected = new DownloadIssueOutput(0);
        //when/then
        this.mockMvc.perform(get(DOWNLOAD_URL))
            .andDo(print())
            .andExpect(status().is2xxSuccessful())
            .andExpect(content().json(objectMapper.writeValueAsString(expected)));
    }
}
