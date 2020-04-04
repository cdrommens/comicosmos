package be.rommens.hera.core;

import org.apache.commons.lang3.StringUtils;

/**
 * User : cederik
 * Date : 04/04/2020
 * Time : 13:24
 */
public interface Mapper<E extends Enum<E>> {

    default Enum<E> mapTo(String value) {
        if (StringUtils.isEmpty(value)) {
            return null;
        }
        String sanitizedStatus = StringUtils.remove(value, StringUtils.SPACE).toUpperCase();
        return mappingLogic(sanitizedStatus);
    }

    Enum<E> mappingLogic(String valueInUppercase);
}
