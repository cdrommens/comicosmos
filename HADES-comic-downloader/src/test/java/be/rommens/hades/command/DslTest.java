package be.rommens.hades.command;

import be.rommens.hades.probeersel.DslExecutor;
import be.rommens.hades.probeersel.Start;
import be.rommens.hades.probeersel.StartImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * User : cederik
 * Date : 08/05/2020
 * Time : 09:44
 */
@Slf4j
public class DslTest {

    @Test
    public void doe() {
        /*Start start = new StartImpl();
        DslExecutor executor = start.startChain("1").next("2").end();
        start = new StartImpl();
        DslExecutor executor2 = start.startChain("1").next("2").onError("e").end();
        start = new StartImpl();
        DslExecutor executor3 = start.withContext("c").startChain("1").onError("e").end();
         */
        Start start = new StartImpl();
        DslExecutor executor4 = start.withPrecondition("pre").onFailed("f").orElse().withContext("p").startChain("d").end();
        /*executor.execute();
        log.info("volgende");
        executor2.execute();
        log.info("volgende");
        executor3.execute();
        log.info("volgende");*/
        executor4.execute();

    }
}
