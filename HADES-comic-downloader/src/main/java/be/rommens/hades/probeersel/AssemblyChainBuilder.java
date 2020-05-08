package be.rommens.hades.probeersel;

import be.rommens.hades.assembler.IssueAssemblyContext;
import be.rommens.hades.core.Command;

import java.lang.reflect.Constructor;
import java.util.LinkedList;

/**
 * User : cederik
 * Date : 07/05/2020
 * Time : 13:58
 */
public class AssemblyChainBuilder {

    private LinkedList<Command> commandStack = new LinkedList<>();
    private IssueAssemblyContext context;
    private Command precondition;
    private Command onError;

    public AssemblyChainBuilder context(IssueAssemblyContext context) {
        this.context = context;
        return this;
    }

    public AssemblyChainBuilder withPrecondition(Class<? extends Command> clazz) throws Exception {
        this.precondition = createCommandObject(clazz);
        return this;
    }

    public AssemblyChainBuilder onPreconditionFailed(Class<? extends Command> clazz) throws Exception {
        this.onError = createCommandObject(clazz);
        return this;
    }

    public AssemblyChainBuilder next(Class<? extends Command> clazz) throws Exception {
        this.commandStack.add(createCommandObject(clazz));
        return this;
    }

    public AssemblyExecutor build() {
        return new AssemblyExecutor(commandStack, context, precondition, onError);
    }

    private Command createCommandObject(Class<? extends Command> clazz) throws Exception {
        Constructor<?> constructor = clazz.getConstructor(IssueAssemblyContext.class);
        return  (Command) constructor.newInstance(context);
    }
}
