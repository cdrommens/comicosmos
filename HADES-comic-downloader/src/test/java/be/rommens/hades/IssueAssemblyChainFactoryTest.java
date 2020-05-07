package be.rommens.hades;

import be.rommens.hades.assembler.DownloadIssueMessage;
import be.rommens.hades.core.AssemblyChainFactory;
import be.rommens.hera.api.Provider;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.AbstractFileHeader;
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
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * User : cederik
 * Date : 27/04/2020
 * Time : 09:07
 */
@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.NONE,
    properties = {
        "providers.url.readcomics=http://localhost:${wiremock.server.port}/",
    }
)
@AutoConfigureWireMock(port = 8977)
public class IssueAssemblyChainFactoryTest {

    private static final String BASE_URL = Paths.get(FileUtils.getTempDirectoryPath(),"junit5/").toString();

    @Autowired
    private AssemblyChainFactory<DownloadIssueMessage> issueAssemblyChainFactory;

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
    public void testIssueAssemblyChain() throws ZipException {
        DownloadIssueMessage downloadIssueMessage = new DownloadIssueMessage();
        downloadIssueMessage.setComicKey("comickey");
        downloadIssueMessage.setProvider(Provider.READCOMICS);
        downloadIssueMessage.setIssueNumber("1");
        issueAssemblyChainFactory.createAssemblyChain(downloadIssueMessage).execute();

        ZipFile expected = new ZipFile(Paths.get(BASE_URL,"comickey","comickey-1.cbz").toFile());
        assertThat(expected.isValidZipFile(), is(Boolean.TRUE));
        assertThat(FileUtils.sizeOf(expected.getFile()), is(greaterThan(10L)));
        assertThat(expected.getFileHeaders().stream().map(AbstractFileHeader::getFileName).collect(Collectors.toList()), hasItem("comickey-1/02.jpg"));
        assertThat(expected.getFileHeaders().stream().map(AbstractFileHeader::getFileName).collect(Collectors.toList()), hasItem("comickey-1/03.jpg"));
        assertThat(Files.exists(Paths.get(BASE_URL, "comickey", "comickey-1")), is(Boolean.FALSE));
    }
}
