package be.rommens.hera.autoconfigure;

import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.properties.PropertyMapping;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//import org.springframework.boot.test.autoconfigure.properties.PropertyMapping;

/**
 * User : cederik
 * Date : 13/05/2020
 * Time : 21:04
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@ImportAutoConfiguration(classes = MockScraperAutoConfiguration.class)
@PropertyMapping("hera.test.scrapermock")
public @interface AutoConfigureScraperMock {

    String value() default "";
}
