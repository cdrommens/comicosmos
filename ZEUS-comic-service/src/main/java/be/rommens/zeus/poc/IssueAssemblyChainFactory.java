package be.rommens.zeus.poc;

import be.rommens.hera.api.service.ScraperFactory;
import be.rommens.hera.core.Scraper;
import be.rommens.zeus.model.Issue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

//TODO : rework to nio

/**
 * User : cederik
 * Date : 22/04/2020
 * Time : 15:24
 */
@Component
public class IssueAssemblyChainFactory implements AssemblyChainFactory<Issue> {

    @Value("${download.folder.base}")
    private String baseUrl;

    @Autowired
    private ScraperFactory scraperFactory;

    @Override
    public Command createAssemblyChain(Issue issue) {
        AssembleIssueContext context = createContextObject(issue);
        return createChain(context);
    }

    private Command createChain(AssembleIssueContext context) {
        CommandStep create = new CreateFolderCommand(context);
        CommandStep getPages = new ScrapeIssueCommand(context);
        CommandStep downloadPages = new DownloadIssuePagesCommand(context);
        CommandStep zip = new ZipFolderCommand(context);
        CommandStep clean = new CleanUpCommand(context);
        // update issue in db command
        // notify mongodb change

        return IssueAssemblyChainBuilder.builderInstance()
            .thenExecute(create)
            .thenExecute(getPages)
            .thenExecute(downloadPages)
            .thenExecute(zip)
            .thenExecute(clean)
            .buildAssemblyChain();
    }

    private AssembleIssueContext createContextObject(Issue issue) {
        Scraper scraper = scraperFactory.createScraper(issue.getComic().getProvider());
        return new AssembleIssueContext(issue, baseUrl, scraper);
    }
}
