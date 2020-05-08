package be.rommens.hades.probeersel;

import be.rommens.hades.assembler.IssueAssemblyContext;
import be.rommens.hades.core.Command;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;

/**
 * User : cederik
 * Date : 07/05/2020
 * Time : 14:07
 */
@Slf4j
@RequiredArgsConstructor
public class AssemblyExecutor {

    private final LinkedList<Command> commandStack;
    private final IssueAssemblyContext context;
    private final Command precondition;
    private final Command onError;

    public boolean execute() {
        if (precondition != null) {
            if (precondition.execute()) {
                // loop over all commands untill one is in error, then reverse back
                for (Command command : commandStack) {
                    if (command.execute()) {
                        break;
                    }
                    else {
                        int index = commandStack.indexOf(command);
                        for (int i = index; i == 0; i-- ) {
                            //command.rollback();
                            log.info("command {} rolled back", command.getClass());
                        }
                    }
                }
            }
            return onError.execute();
        }
        for (Command command : commandStack) {
            if (command.execute()) {
                break;
            }
            else {
                int index = commandStack.indexOf(command);
                for (int i = index; i == 0; i-- ) {
                    //command.rollback();
                    log.info("command {} rolled back", command.getClass());
                }
            }
        }
        return false;
    }
}
