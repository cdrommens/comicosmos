package be.rommens.hades;

import be.rommens.hades.assembler.Issue;
import be.rommens.hades.core.AssemblyChainFactory;
import be.rommens.hera.api.Provider;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * User : cederik
 * Date : 27/04/2020
 * Time : 09:07
 */
@SpringBootTest(
    properties = {
        "providers.url.readcomics=http://localhost:${wiremock.server.port}/"
    }
)
@AutoConfigureWireMock(port = 8977)
public class IssueAssemblyChainFactoryIT {

    private static final String BASE_URL = Paths.get(FileUtils.getTempDirectoryPath(),"junit5/").toString();

    @Autowired
    private AssemblyChainFactory<Issue> issueAssemblyChainFactory;

    @DynamicPropertySource
    static void dynamicProperties(DynamicPropertyRegistry registry) {
        registry.add("download.folder.base", () -> BASE_URL);
    }

    @BeforeEach
    public void setUp() throws IOException {
        FileUtils.forceMkdir(Paths.get(BASE_URL).toFile());
    }

    @AfterEach
    public void TearDown() throws IOException {
        FileUtils.deleteDirectory(Paths.get(BASE_URL).toFile());
    }

    @Test
    public void testIssueAssemblyChain() {
        Issue issue = new Issue();
        issue.setComicKey("comickey");
        issue.setProvider(Provider.READCOMICS);
        issue.setIssueNumber("1");
        issueAssemblyChainFactory.createAssemblyChain(issue).execute();
    }
}
