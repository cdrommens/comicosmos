package be.rommens.hades.command;

import be.rommens.hades.core.Command;

/**
 * User : cederik
 * Date : 28/04/2020
 * Time : 15:40
 */
public class MockCommand implements Command {

    @Override
    public boolean execute() {
        return true;
    }
}
