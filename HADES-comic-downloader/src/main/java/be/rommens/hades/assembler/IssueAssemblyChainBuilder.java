package be.rommens.hades.assembler;

import be.rommens.hades.core.CommandStep;

/**
 * User : cederik
 * Date : 22/04/2020
 * Time : 15:18
 */
public final class IssueAssemblyChainBuilder {

    private CommandStep firstCommand;
    private CommandStep lastCommand;

    private IssueAssemblyChainBuilder() {

    }

    public static IssueAssemblyChainBuilder builderInstance() {
        return new IssueAssemblyChainBuilder();
    }

    public IssueAssemblyChainBuilder thenExecute(CommandStep command) {
        if (firstCommand == null) {
            this.firstCommand = command;
            this.lastCommand = command;
            return this;
        }
        lastCommand.linkWith(command);
        lastCommand = command;
        return this;
    }

    public CommandStep buildAssemblyChain() {
        return firstCommand;
    }
}
