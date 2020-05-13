package be.rommens.hermes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;

/**
 * User : cederik
 * Date : 12/05/2020
 * Time : 14:45
 */
@SpringBootApplication
@EnableBinding(Source.class)
public class HermesApplication {

    public static void main(String[] args) {
        SpringApplication.run(HermesApplication.class, args);
    }
}
