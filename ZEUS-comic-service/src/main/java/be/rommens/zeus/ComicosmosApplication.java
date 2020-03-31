package be.rommens.zeus;

import be.rommens.hera.providers.ReadComicsScraper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@Import(ReadComicsScraper.class)
@RestController
public class ComicosmosApplication {

    private final ReadComicsScraper dependency;

    public ComicosmosApplication(ReadComicsScraper dependency) {
        this.dependency = dependency;
    }

    @GetMapping("/")
    public String home() {
        return "Hello world! - " + dependency.getProviderProperty();
    }

    public static void main(String[] args) {
        SpringApplication.run(ComicosmosApplication.class, args);
    }

}
