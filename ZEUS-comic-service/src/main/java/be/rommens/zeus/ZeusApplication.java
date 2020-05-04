package be.rommens.zeus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EnableBinding(Source.class)
public class ZeusApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZeusApplication.class, args);
    }

}
