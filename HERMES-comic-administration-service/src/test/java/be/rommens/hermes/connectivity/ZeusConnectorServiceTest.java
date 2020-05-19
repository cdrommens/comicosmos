package be.rommens.hermes.connectivity;

import be.rommens.hermes.model.input.IssueToDownloadList;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;

import static be.rommens.hermes.model.IssueToDownloadListTestObjectFactory.getEmptyIssueToDownloadList;
import static be.rommens.hermes.model.IssueToDownloadListTestObjectFactory.getIssueToDownloadListWith2Issues;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

/**
 * User : cederik
 * Date : 13/05/2020
 * Time : 09:33
 */
@RestClientTest(ZeusConnectorService.class)
class ZeusConnectorServiceTest {

    @Autowired
    private ZeusConnectorService zeusConnectorService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockRestServiceServer mockRestServiceServer;

    @Test
    void whenServiceIsCalled_returnList() throws JsonProcessingException {
        //given
        String expected = objectMapper.writeValueAsString(getIssueToDownloadListWith2Issues());
        this.mockRestServiceServer.expect(requestTo("/issue/todownload"))
            .andRespond(withSuccess(expected, MediaType.APPLICATION_JSON));
        //when
        IssueToDownloadList result = zeusConnectorService.getIssuesToDownload();

        //then
        assertThat(result).isEqualToComparingFieldByField(getIssueToDownloadListWith2Issues());
    }

    @Test
    void whenServiceIsCalled_returnEmptyList() throws JsonProcessingException {
        //given
        String expected = objectMapper.writeValueAsString(getEmptyIssueToDownloadList());
        this.mockRestServiceServer.expect(requestTo("/issue/todownload"))
            .andRespond(withSuccess(expected, MediaType.APPLICATION_JSON));
        //when
        IssueToDownloadList result = zeusConnectorService.getIssuesToDownload();

        //then
        assertThat(result).isEqualToComparingFieldByField(getEmptyIssueToDownloadList());
    }
}
