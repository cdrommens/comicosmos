package be.rommens.hera.providers;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.cloud.contract.wiremock.WireMockSpring.options;

/**
 * User : cederik
 * Date : 29/03/2020
 * Time : 14:22
 */
@SpringBootTest(classes = ReadComicsScraper.class)
@AutoConfigureWireMock(port = 8888)
public class ReadComicsScraperTest {

    @Autowired
    private ReadComicsScraper readComicsScraper;

    @Test
    public void test() {
        assertThat(readComicsScraper.getProviderProperty(), is("http://readcomicsonline.ru/comic/"));
    }

    @Test
    public void testW() throws IOException {
        String result = readComicsScraper.testConn();
        assertThat(result, Matchers.containsString("world"));
    }
}
