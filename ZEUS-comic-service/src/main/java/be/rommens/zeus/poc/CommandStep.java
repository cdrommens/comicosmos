package be.rommens.zeus.poc;

/**
 * User : cederik
 * Date : 24/04/2020
 * Time : 20:10
 */
public interface CommandStep extends Command {
    CommandStep linkWith(CommandStep next);

    CommandResult body();
}
