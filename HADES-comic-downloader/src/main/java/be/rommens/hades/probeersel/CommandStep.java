package be.rommens.hades.probeersel;

import be.rommens.hades.core.Command;
import be.rommens.hades.core.CommandResult;

/**
 * User : cederik
 * Date : 24/04/2020
 * Time : 20:10
 */
public interface CommandStep extends Command {

    CommandResult body();
}
