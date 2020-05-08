package be.rommens.zeus.controller;

import be.rommens.zeus.service.IssueService;
import be.rommens.zeus.web.IssueController;
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
        given(issueService.downloadNewIssues()).willReturn(1);
        RestAssuredMockMvc.standaloneSetup(new IssueController(issueService));
    }
}
