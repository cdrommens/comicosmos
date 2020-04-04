package be.rommens.hera.providers;

import be.rommens.hera.api.Status;
import be.rommens.hera.providers.readcomics.mappers.StatusMapper;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * User : cederik
 * Date : 04/04/2020
 * Time : 13:16
 */
public class StatusMapperTest {

    @Test
    public void testMapToOngoing() {
        assertThat(new StatusMapper().mapTo("Ongoing"), is(Status.ONGOING));
        assertThat(new StatusMapper().mapTo("ongoing"), is(Status.ONGOING));
        assertThat(new StatusMapper().mapTo("ONGOING"), is(Status.ONGOING));
    }

    @Test
    public void testMapToFinished() {
        assertThat(new StatusMapper().mapTo("Complete"), is(Status.FINISHED));
        assertThat(new StatusMapper().mapTo("complete"), is(Status.FINISHED));
        assertThat(new StatusMapper().mapTo("COMPLETE"), is(Status.FINISHED));
    }
}
