package be.rommens.hades.command;

import be.rommens.hades.assembler.IssueAssemblyContext;
import be.rommens.hades.core.CommandResult;
import be.rommens.hades.core.CommandStep;
import lombok.RequiredArgsConstructor;

/**
 * User : cederik
 * Date : 20/04/2020
 * Time : 19:51
 */
@RequiredArgsConstructor
public abstract class AbstractCommand implements CommandStep {

    public final IssueAssemblyContext issueAssemblyContext;

    private CommandStep next;

    private boolean nextExecute() {
        if (next == null) {
            return false;
        }
        return next.execute();
    }

    @Override
    public CommandStep linkWith(CommandStep next) {
        this.next = next;
        return next;
    }

    @Override
    public boolean execute() {
        CommandResult result = body();
        if (CommandResult.COMPLETED == result) {
            return nextExecute();
        }
        return false;
    }
}
