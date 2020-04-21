package be.rommens.zeus.poc;

/**
 * User : cederik
 * Date : 20/04/2020
 * Time : 19:51
 */
public abstract class Command {

    public DownloadAndCreateZip downloadAndCreateZip;
    private Command next;

    Command(DownloadAndCreateZip downloadAndCreateZip) {
        this.downloadAndCreateZip = downloadAndCreateZip;
    }

    public Command linkWith(Command next) {
        this.next = next;
        return next;
    }
    public abstract boolean execute();  // CoC : check command

    protected boolean nextExecute() {
        if (next == null) {
            return true;
        }
        return next.execute();
    }
}
