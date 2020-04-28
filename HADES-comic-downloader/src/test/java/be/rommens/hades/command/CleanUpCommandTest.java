package be.rommens.hades.command;

import be.rommens.hades.core.CommandResult;
import be.rommens.hades.model.IssueAssemblyContextTestObjectFactory;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * User : cederik
 * Date : 26/04/2020
 * Time : 14:27
 */
public class CleanUpCommandTest {

    @TempDir
    Path tempDir;

    @Test
    public void testDeleteDir() throws IOException {
        File newDir = Paths.get(tempDir.toAbsolutePath().toString(), "comickey", "comickey-1").toFile();
        FileUtils.forceMkdir(newDir);
        CleanUpCommand command = new CleanUpCommand(IssueAssemblyContextTestObjectFactory.createTestContext(tempDir.toString(), null));
        CommandResult result = command.body();
        assertThat(result, is(CommandResult.COMPLETED));
        assertThat(Files.exists(Paths.get(tempDir.toAbsolutePath().toString(), "comickey", "comickey-1")), is(Boolean.FALSE));
    }
}
