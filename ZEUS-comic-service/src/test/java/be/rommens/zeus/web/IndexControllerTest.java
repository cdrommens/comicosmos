package be.rommens.zeus.web;

import be.rommens.hera.ScraperTestFactory;
import be.rommens.hera.api.Provider;
import be.rommens.hera.api.service.ScraperFactory;
import be.rommens.hera.builders.ScrapedComicBuilder;
import be.rommens.zeus.model.PublisherTestObjectFactory;
import be.rommens.zeus.repository.PublisherRepository;
import be.rommens.zeus.service.ComicService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * User : cederik
 * Date : 09/04/2020
 * Time : 14:52
 */
@WebMvcTest(IndexController.class)
public class IndexControllerTest {

    @MockBean
    private ScraperFactory scraperFactory;

    @MockBean
    private PublisherRepository publisherRepository;

    @MockBean
    private ComicService comicService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturnDefaultMessage() throws Exception {
        given(scraperFactory.createScraper(any(Provider.class)))
            .willReturn(ScraperTestFactory.willReturnScrapedComic(
                new ScrapedComicBuilder().title("blablabla").build()
            ));
        given(publisherRepository.findById(any())).willReturn(Optional.of(PublisherTestObjectFactory.getDcComicsPublisher()));
        this.mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk())
            .andExpect(content().string(containsString("Hello world")));
    }
}
