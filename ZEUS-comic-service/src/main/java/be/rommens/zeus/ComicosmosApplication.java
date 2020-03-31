package be.rommens.zeus;

import be.rommens.hera.Dependency;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@Import(Dependency.class)
@RestController
public class ComicosmosApplication {

    private final Dependency dependency;

    public ComicosmosApplication(Dependency dependency) {
        this.dependency = dependency;
    }

    @GetMapping("/")
    public String home() {
        dependency.setName("naam");
        return "Hello world! - " + dependency.hello();
    }

    public static void main(String[] args) {
        SpringApplication.run(ComicosmosApplication.class, args);
    }

}
