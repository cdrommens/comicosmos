package be.rommens.hera.providers;

import be.rommens.hera.api.Publisher;
import be.rommens.hera.providers.readcomics.mappers.PublisherMapper;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * User : cederik
 * Date : 03/04/2020
 * Time : 21:00
 */
public class PublisherMapperTest {

    @Test
    public void testMapToDcComics() {
        assertThat(Publisher.DC_COMICS, is(new PublisherMapper().mapTo("Dc Comics")));
        assertThat(Publisher.DC_COMICS, is(new PublisherMapper().mapTo("DcComics")));
        assertThat(Publisher.DC_COMICS, is(new PublisherMapper().mapTo("dc comics")));
        assertThat(Publisher.DC_COMICS, is(new PublisherMapper().mapTo("dccomics")));
    }

    @Test
    public void testMapToMarvelComics() {
        assertThat(Publisher.MARVEL_COMICS, is(new PublisherMapper().mapTo("Marvel Comics")));
        assertThat(Publisher.MARVEL_COMICS, is(new PublisherMapper().mapTo("MarvelComics")));
        assertThat(Publisher.MARVEL_COMICS, is(new PublisherMapper().mapTo("marvel comics")));
        assertThat(Publisher.MARVEL_COMICS, is(new PublisherMapper().mapTo("marvelcomics")));
    }
}
