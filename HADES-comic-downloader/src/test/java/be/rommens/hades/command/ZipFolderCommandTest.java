package be.rommens.hades.command;

import be.rommens.hades.core.CommandResult;
import be.rommens.hades.model.IssueAssemblyContextTestObjectFactory;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.model.AbstractFileHeader;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

/**
 * User : cederik
 * Date : 26/04/2020
 * Time : 14:52
 */
public class ZipFolderCommandTest {

    @TempDir
    Path tempDir;

    @Test
    public void testCreateZipEmptyFolder() throws IOException {
        File newDir = Paths.get(tempDir.toAbsolutePath().toString(), "comickey", "comickey-1").toFile();
        FileUtils.forceMkdir(newDir);
        assertThat(Files.exists(Paths.get(tempDir.toAbsolutePath().toString(), "comickey", "comickey-1")), is(Boolean.TRUE));

        ZipFolderCommand command = new ZipFolderCommand(IssueAssemblyContextTestObjectFactory.createTestContext(tempDir.toString(), null));
        CommandResult result = command.body();
        assertThat(result, is(CommandResult.ERROR));
    }

    @Test
    public void testCreateZip() throws IOException {
        File newDir = Paths.get(tempDir.toAbsolutePath().toString(), "comickey", "comickey-1").toFile();
        FileUtils.forceMkdir(newDir);
        assertThat(Files.exists(Paths.get(tempDir.toAbsolutePath().toString(), "comickey", "comickey-1")), is(Boolean.TRUE));
        File page1 = new File(newDir, "page1");
        File page2 = new File(newDir, "page2");
        FileUtils.touch(page1);
        FileUtils.touch(page2);
        assertThat(Files.exists(page1.toPath()), is(Boolean.TRUE));
        assertThat(Files.exists(page2.toPath()), is(Boolean.TRUE));

        ZipFolderCommand command = new ZipFolderCommand(IssueAssemblyContextTestObjectFactory.createTestContext(tempDir.toString(), null));
        CommandResult result = command.body();
        assertThat(result, is(CommandResult.COMPLETED));

        ZipFile expected = new ZipFile(Paths.get(tempDir.toAbsolutePath().toString(), "comickey", "comickey-1.cbz").toFile());
        assertThat(expected.isValidZipFile(), is(Boolean.TRUE));
        assertThat(expected.getFileHeaders().stream().map(AbstractFileHeader::getFileName).collect(Collectors.toList()), hasItem("comickey-1/page1"));
        assertThat(expected.getFileHeaders().stream().map(AbstractFileHeader::getFileName).collect(Collectors.toList()), hasItem("comickey-1/page2"));
    }
}
