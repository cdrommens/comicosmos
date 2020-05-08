package be.rommens.hades.probeersel;

import be.rommens.hades.assembler.IssueAssemblyContext;
import be.rommens.hades.core.CommandResult;
import lombok.RequiredArgsConstructor;

/**
 * User : cederik
 * Date : 20/04/2020
 * Time : 19:51
 */
@RequiredArgsConstructor
public abstract class AbstractCommand implements CommandStep {

    public final IssueAssemblyContext issueAssemblyContext;

    @Override
    public boolean execute() {
        CommandResult result = body();
        return CommandResult.COMPLETED == result;
    }
}
