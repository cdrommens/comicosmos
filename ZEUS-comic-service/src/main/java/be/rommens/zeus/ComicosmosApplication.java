package be.rommens.zeus;

import be.rommens.hera.Dependency;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class ComicosmosApplication {

	public static void main(String[] args) {
		SpringApplication.run(ComicosmosApplication.class, args);
	}

	@GetMapping("/")
	public String home() {
            Dependency dependency = new Dependency("testje");
	    return "Hello world! - " + dependency.hello();
        }

}
