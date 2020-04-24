package be.rommens.zeus.poc;

/**
 * User : cederik
 * Date : 20/04/2020
 * Time : 19:51
 */
public abstract class AbstractCommand implements Command {

    public final AssembleIssueContext assembleIssueContext;
    private AbstractCommand next;

    protected AbstractCommand(AssembleIssueContext assembleIssueContext) {
        this.assembleIssueContext = assembleIssueContext;
    }


    public AbstractCommand linkWith(AbstractCommand next) {
        this.next = next;
        return next;
    }

    protected boolean nextExecute() {
        if (next == null) {
            return false;
        }
        return next.execute();
    }

    protected abstract CommandResult body();

    @Override
    public boolean execute() {
        CommandResult result = body();
        if (CommandResult.COMPLETED == result) {
            return nextExecute();
        }
        return false;
    }
}
