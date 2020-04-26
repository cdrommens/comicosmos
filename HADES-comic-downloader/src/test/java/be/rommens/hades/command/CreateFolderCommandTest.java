package be.rommens.hades.command;

import be.rommens.hades.core.CommandResult;
import be.rommens.hades.model.IssueAssemblyContextTestObjectFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * User : cederik
 * Date : 26/04/2020
 * Time : 13:57
 */
public class CreateFolderCommandTest {

    @TempDir
    Path tempDir;

    @Test
    public void testIfFolderIsCreated() {
        CreateFolderCommand command = new CreateFolderCommand(IssueAssemblyContextTestObjectFactory.createTestContext(tempDir.toString(), null));
        CommandResult result = command.body();
        assertThat(result, is(CommandResult.COMPLETED));
        assertThat(Files.exists(Paths.get(tempDir.toAbsolutePath().toString(), "comickey", "comickey-1")), is(Boolean.TRUE));
    }
}
