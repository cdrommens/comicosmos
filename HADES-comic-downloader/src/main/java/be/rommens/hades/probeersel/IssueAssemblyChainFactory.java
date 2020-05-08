package be.rommens.hades.probeersel;

import be.rommens.hades.assembler.DownloadIssueMessage;
import be.rommens.hades.assembler.IssueAssemblyContext;
import be.rommens.hera.api.service.ScraperFactory;
import be.rommens.hera.core.Scraper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * User : cederik
 * Date : 07/05/2020
 * Time : 14:21
 */
@Component
@RequiredArgsConstructor
public class IssueAssemblyChainFactory {

    @Value("${download.folder.base}")
    private String baseUrl;

    private final ScraperFactory scraperFactory;

    public void createAssemblyChain(DownloadIssueMessage downloadIssueMessage) throws Exception {
        IssueAssemblyContext context = createContextObject(downloadIssueMessage);
        AssemblyChainBuilder assemblyChainBuilder = new AssemblyChainBuilder();
        assemblyChainBuilder
            .context(context)
            .withPrecondition(ZipNotExistCommand.class)
            .onPreconditionFailed(PublishIssueDownloadedMessage.class)
            .next(CreateFolderCommand.class)
            .next(ScrapeIssueCommand.class)
            .next(DownloadIssuePagesCommand.class)
            .next(ZipFolderCommand.class)
            .next(CleanUpCommand.class)
            .next(PublishIssueDownloadedMessage.class)
            .build()
        .execute();
    }

    private IssueAssemblyContext createContextObject(DownloadIssueMessage downloadIssueMessage) {
        Scraper scraper = scraperFactory.createScraper(downloadIssueMessage.getProvider());
        return new IssueAssemblyContext(downloadIssueMessage, baseUrl, scraper);
    }
}
