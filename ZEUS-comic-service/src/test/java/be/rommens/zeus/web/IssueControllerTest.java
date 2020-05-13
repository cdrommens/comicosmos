package be.rommens.zeus.web;

import be.rommens.hera.api.Provider;
import be.rommens.hera.api.Status;
import be.rommens.zeus.model.builder.ComicBuilder;
import be.rommens.zeus.model.builder.IssueBuilder;
import be.rommens.zeus.model.entity.Comic;
import be.rommens.zeus.model.output.IssueToDownload;
import be.rommens.zeus.model.output.IssueToDownloadList;
import be.rommens.zeus.service.IssueService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Collections;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * User : cederik
 * Date : 05/05/2020
 * Time : 09:46
 */
@WebMvcTest(controllers = IssueController.class)
public class IssueControllerTest {

    private static final String TO_DOWNLOAD_URL = "/issue/todownload";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private IssueService issueService;

    @Test
    public void whenIssuesFound_returnIssueToDownloadList() throws Exception {
        //given
        Comic comic = ComicBuilder.aComic()
            .key("batman-2016")
            .provider(Provider.READCOMICS)
            .status(Status.ONGOING)
            .issue(IssueBuilder.anIssue()
                .issueId(-3)
                .issueNumber("Annual-1")
                .dateOfRelease(LocalDate.of(2016, 2, 1)))
            .issue(IssueBuilder.anIssue()
                .issueId(-4)
                .issueNumber("1")
                .dateOfRelease(LocalDate.of(2018, 2, 1)))
            .build();
        given(issueService.downloadNewIssues()).willReturn(comic.getIssues());

        IssueToDownloadList expected = new IssueToDownloadList();
        IssueToDownload issue1 = new IssueToDownload();
        issue1.setIssueId(-3);
        issue1.setComicKey("batman-2016");
        issue1.setProvider(Provider.READCOMICS);
        issue1.setIssueNumber("Annual-1");
        issue1.setDateOfRelease(LocalDate.of(2016, 2, 1));
        IssueToDownload issue2 = new IssueToDownload();
        issue2.setIssueId(-4);
        issue2.setComicKey("batman-2016");
        issue2.setProvider(Provider.READCOMICS);
        issue2.setIssueNumber("1");
        issue2.setDateOfRelease(LocalDate.of(2018, 2, 1));
        expected.add(issue1);
        expected.add(issue2);

        //when/then
        this.mockMvc.perform(get(TO_DOWNLOAD_URL))
            .andDo(print())
            .andExpect(status().is2xxSuccessful())
            .andExpect(content().json(objectMapper.writeValueAsString(expected)));
    }

    @Test
    public void whenNoIssuesFound_returnIssueToDownloadListWithEmptyList() throws Exception {
        //given
        given(issueService.downloadNewIssues()).willReturn(Collections.emptyList());
        IssueToDownloadList expected = new IssueToDownloadList();

        //when/then
        this.mockMvc.perform(get(TO_DOWNLOAD_URL))
            .andDo(print())
            .andExpect(status().is2xxSuccessful())
            .andExpect(content().json(objectMapper.writeValueAsString(expected)));
    }
}
