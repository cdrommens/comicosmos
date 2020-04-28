package be.rommens.hades.assembler;

import be.rommens.hades.command.CleanUpCommand;
import be.rommens.hades.command.CreateFolderCommand;
import be.rommens.hades.command.DownloadIssuePagesCommand;
import be.rommens.hades.command.ScrapeIssueCommand;
import be.rommens.hades.command.ZipFolderCommand;
import be.rommens.hades.core.AssemblyChainFactory;
import be.rommens.hades.core.Command;
import be.rommens.hades.core.CommandStep;
import be.rommens.hera.api.service.ScraperFactory;
import be.rommens.hera.core.Scraper;
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
        IssueAssemblyContext context = createContextObject(issue);
        return createChain(context);
    }

    private Command createChain(IssueAssemblyContext context) {
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

    private IssueAssemblyContext createContextObject(Issue issue) {
        Scraper scraper = scraperFactory.createScraper(issue.getProvider());
        return new IssueAssemblyContext(issue, baseUrl, scraper);
    }
}
