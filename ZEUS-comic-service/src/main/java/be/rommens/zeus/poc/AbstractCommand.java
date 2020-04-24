package be.rommens.zeus.poc;

/**
 * User : cederik
 * Date : 20/04/2020
 * Time : 19:51
 */
public abstract class AbstractCommand implements CommandStep {

    public final AssembleIssueContext assembleIssueContext;
    private CommandStep next;

    AbstractCommand(AssembleIssueContext assembleIssueContext) {
        this.assembleIssueContext = assembleIssueContext;
    }

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
