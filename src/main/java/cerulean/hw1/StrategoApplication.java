package cerulean.hw1;

import cerulean.hw1.controllers.PlayerController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@ComponentScan
@EnableAutoConfiguration
@EnableMongoRepositories
public class StrategoApplication {

	public static void main(String[] args) {
		SpringApplication.run(StrategoApplication.class, args);
	}

}
