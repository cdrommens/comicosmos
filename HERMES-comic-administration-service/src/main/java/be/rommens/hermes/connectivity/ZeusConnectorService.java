package be.rommens.hermes.connectivity;

import be.rommens.hermes.model.input.IssueToDownloadList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

/**
 * User : cederik
 * Date : 13/05/2020
 * Time : 09:10
 */
@Slf4j
@Service
public class ZeusConnectorService {

    private final RestTemplate restTemplate;

    public ZeusConnectorService(RestTemplateBuilder restTemplateBuilder, @Value("${service.zeus.hostname}") String zeusUri) {
        Assert.notNull(zeusUri, () -> "zeusUri can't be null");
        this.restTemplate = restTemplateBuilder
            .rootUri(zeusUri)
            .build();
    }

    public IssueToDownloadList getIssuesToDownload() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        IssueToDownloadList issueToDownloadList = restTemplate
            .exchange("/issue/todownload", HttpMethod.GET, requestEntity, IssueToDownloadList.class)
            .getBody();
        if (issueToDownloadList == null) {
            throw new IllegalStateException("Nothing fetched from endpoint");
        }
        return issueToDownloadList;
    }
}
