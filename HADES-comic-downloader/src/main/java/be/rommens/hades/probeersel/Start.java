package be.rommens.hades.probeersel;

/**
 * User : cederik
 * Date : 08/05/2020
 * Time : 09:42
 */
public interface Start {
    StartChain withContext(String param);
    Precondition withPrecondition(String param);
    InterChain startChain(String param);


    interface Context extends StartChain {
        StartChain withContext(String param);
    }

    interface Precondition extends PreconditionOrElse {
        PreconditionOrElse onFailed(String param);
    }

    interface PreconditionOrElse {
        Context orElse();
    }

    interface StartChain extends End {
        InterChain startChain(String param);
    }

    interface InterChain extends End{
        InterChain next(String param);
        End onError(String param);
    }

    interface End {
        DslExecutor end();
    }
}
