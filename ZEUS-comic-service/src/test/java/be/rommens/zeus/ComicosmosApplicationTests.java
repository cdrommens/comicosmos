package be.rommens.zeus;

import be.rommens.hera.ScraperTestFactory;
import be.rommens.hera.api.Provider;
import be.rommens.hera.api.service.ScraperFactory;
import be.rommens.hera.builders.ScrapedComicBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ComicosmosApplication.class)
class ComicosmosApplicationTests {

    @MockBean
    private ScraperFactory scraperFactory;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturnDefaultMessage() throws Exception {
        given(scraperFactory.createScraper(any(Provider.class)))
            .willReturn(ScraperTestFactory.willReturnScrapedComic(
                new ScrapedComicBuilder().title("blablabla").build()
            ));
        this.mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk())
            .andExpect(content().string(containsString("Hello world")));
    }
}
