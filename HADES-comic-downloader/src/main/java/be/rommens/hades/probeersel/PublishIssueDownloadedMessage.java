package be.rommens.hades.probeersel;

import be.rommens.hades.assembler.IssueAssemblyContext;
import be.rommens.hades.core.CommandResult;
import lombok.extern.slf4j.Slf4j;

/**
 * User : cederik
 * Date : 07/05/2020
 * Time : 16:17
 */
@Slf4j
public class PublishIssueDownloadedMessage extends AbstractCommand {

    public PublishIssueDownloadedMessage(IssueAssemblyContext issueAssemblyContext) {
        super(issueAssemblyContext);
    }

    @Override
    public CommandResult body() {
        //TODO : implement publish the message
        log.info("published message");
        return CommandResult.COMPLETED;
    }
}
