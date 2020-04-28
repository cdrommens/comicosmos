package be.rommens.hades.model;

import be.rommens.hades.assembler.Issue;
import be.rommens.hades.assembler.IssueAssemblyContext;
import be.rommens.hera.core.Scraper;
import org.apache.commons.lang3.StringUtils;

import java.nio.file.Paths;

/**
 * User : cederik
 * Date : 26/04/2020
 * Time : 13:58
 */
public class IssueAssemblyContextTestObjectFactory {

    public static IssueAssemblyContext createTestContext(String baseUrl, Scraper scraper) {
        Issue issue = new Issue();
        issue.setComicKey("comickey");
        issue.setIssueNumber("1");
        IssueAssemblyContext context = new IssueAssemblyContext(issue, baseUrl, scraper);
        if (StringUtils.isNotEmpty(baseUrl)) {
            context.setIssueFolder(Paths.get(baseUrl, "comickey", "comickey-1").toString());
        }
        return context;
    }
}
