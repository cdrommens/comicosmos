package be.rommens.hermes.controller;

import be.rommens.hermes.service.IssueService;
import be.rommens.hermes.web.IssueController;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;

import static org.mockito.BDDMockito.given;

/**
 * User : cederik
 * Date : 05/05/2020
 * Time : 13:20
 */
public class ControllerBaseClass {

    @BeforeEach
    public void setup() {
        IssueService issueService = Mockito.mock(IssueService.class);

        given(issueService.downloadNewIssues()).willReturn(2);

        RestAssuredMockMvc.standaloneSetup(new IssueController(issueService));
    }
}
