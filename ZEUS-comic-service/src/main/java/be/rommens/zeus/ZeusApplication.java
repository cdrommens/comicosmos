package be.rommens.zeus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class ZeusApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZeusApplication.class, args);
    }

}
