package be.rommens.hera.providers;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.TestPropertySource;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * User : cederik
 * Date : 29/03/2020
 * Time : 14:22
 *
 * UseFull links
 * -------------
 * Wiremock recording : http://wiremock.org/docs/record-playback/
 */
@SpringBootTest(classes = ReadComicsScraper.class)
@AutoConfigureWireMock(port = 8888)
@TestPropertySource("classpath:providers-test.properties")
public class ReadComicsScraperTest {

    @Autowired
    private ReadComicsScraper readComicsScraper;

    @Test
    public void test() {
        assertThat(readComicsScraper.getProviderProperty(), is("http://localhost:8888/readcomics"));
    }

    @Test
    public void testW() throws IOException {
        String result = readComicsScraper.testConn();
        assertThat(result, Matchers.containsString("Batman"));
    }

}
