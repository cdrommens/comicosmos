package be.rommens.hades.probeersel;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * User : cederik
 * Date : 08/05/2020
 * Time : 09:45
 */
@Slf4j
public class StartImpl implements Start, Start.Context, Start.Precondition, Start.PreconditionOrElse, Start.StartChain, Start.InterChain, Start.End {

    private String context;
    private String precondition;
    private String preconditionOnFailed;
    private final List<String> queue = new ArrayList<>();
    private String onError;

    @Override
    public StartChain withContext(String param) {
        this.context = param;
        return this;
    }

    @Override
    public Precondition withPrecondition(String param) {
        this.precondition = param;
        return this;
    }

    @Override
    public PreconditionOrElse onFailed(String param) {
        this.preconditionOnFailed = param;
        return this;
    }

    @Override
    public Context orElse() {
        return this;
    }

    @Override
    public InterChain startChain(String param) {
        queue.add(param);
        return this;
    }

    @Override
    public InterChain next(String param) {
        queue.add(param);
        return this;
    }

    @Override
    public End onError(String param) {
        this.onError = param;
        return this;
    }

    @Override
    public DslExecutor end() {
        return new DslExecutor(context, precondition, preconditionOnFailed, queue, onError);
    }
}
