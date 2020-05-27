package be.rommens.zeus.controller;

import be.rommens.hera.api.Provider;
import be.rommens.hera.api.Status;
import be.rommens.zeus.model.builder.ComicBuilder;
import be.rommens.zeus.model.builder.IssueBuilder;
import be.rommens.zeus.model.entity.Comic;
import be.rommens.zeus.service.IssueServiceImpl;
import be.rommens.zeus.web.IssueController;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;

import java.time.LocalDate;

import static org.mockito.BDDMockito.given;

/**
 * User : cederik
 * Date : 05/05/2020
 * Time : 13:20
 */
public class ControllerBaseClass {

    @BeforeEach
    public void setup() {
        IssueServiceImpl issueService = Mockito.mock(IssueServiceImpl.class);

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

        RestAssuredMockMvc.standaloneSetup(new IssueController(issueService));
    }
}
