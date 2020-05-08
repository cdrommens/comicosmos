package be.rommens.hades.probeersel;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * User : cederik
 * Date : 08/05/2020
 * Time : 09:59
 */
@Slf4j
@RequiredArgsConstructor
public class DslExecutor {

    private final String context;
    private final String precondition;
    private final String preconditionOnFailed;
    private final List<String> queue;
    private final String onError;

    public void execute() {
        if (StringUtils.isNotEmpty(precondition)) {
            log.info("precondition {}", precondition);
            if (StringUtils.isNotEmpty(preconditionOnFailed)) {
                log.info("preconditionOnFailed {}", preconditionOnFailed);
                return;
            }
        }
        if (StringUtils.isNotEmpty(context)) {
            log.info("context {}", context);
        }
        queue.forEach(log::info);
        if (StringUtils.isNotEmpty(onError)) {
            log.info("onError {}", onError);
            return;
        }
    }
}
