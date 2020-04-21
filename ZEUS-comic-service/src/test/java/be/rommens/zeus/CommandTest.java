package be.rommens.zeus;

import be.rommens.zeus.poc.DownloadAndCreateZip;
import be.rommens.zeus.poc.DownloadAndCreateZipFactory;
import be.rommens.zeus.poc.IssueAssembler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

/**
 * User : cederik
 * Date : 20/04/2020
 * Time : 20:07
 */
@SpringBootTest
public class CommandTest {

    @Autowired
    private IssueAssembler issueAssembler;

    @Autowired
    private DownloadAndCreateZipFactory factory;

    @Test
    public void test() {
        DownloadAndCreateZip e = factory.createAction("test",
            Arrays.asList("https://www.google.be/images/branding/googlelogo/2x/googlelogo_color_272x92dp.png",
            "https://s.yimg.com/rz/p/yahoo_frontpage_en-US_s_f_p_205x58_frontpage_2x.png"));
        issueAssembler.addToQueue(e);

        issueAssembler.assemble();
    }
}
