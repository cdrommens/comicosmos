package be.rommens.zeus.poc;

/**
 * User : cederik
 * Date : 22/04/2020
 * Time : 15:18
 */
public final class IssueAssemblyChainBuilder {

    private AbstractCommand firstCommand;
    private AbstractCommand lastCommand;

    private IssueAssemblyChainBuilder() {

    }

    public static IssueAssemblyChainBuilder builderInstance() {
        return new IssueAssemblyChainBuilder();
    }

    public IssueAssemblyChainBuilder thenExecute(AbstractCommand command) {
        if (firstCommand == null) {
            this.firstCommand = command;
            this.lastCommand = command;
            return this;
        }
        lastCommand.linkWith(command);
        lastCommand = command;
        return this;
    }

    public AbstractCommand buildAssemblyChain() {
        return firstCommand;
    }
}
