package be.rommens.hera.providers;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * User : cederik
 * Date : 29/03/2020
 * Time : 14:22
 */
public class ReadComicsScraperTest {

    private WireMockServer wireMockServer;

    @BeforeEach
    void setupWireMockServer() {
        this.wireMockServer = new WireMockServer(options()
            .dynamicPort());
        this.wireMockServer.start();
    }

    @AfterEach
    void stopWireMockServer() {
        this.wireMockServer.stop();
    }

    @Test
    public void test() throws IOException {
        ReadComicsScraper scraper = new ReadComicsScraper();
        String result = scraper.testConn();
        assertThat(result, Matchers.containsString("world"));
    }
}
