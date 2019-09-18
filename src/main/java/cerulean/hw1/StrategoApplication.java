package cerulean.hw1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
@EnableAutoConfiguration
@SpringBootApplication
public class StrategoApplication {

	public static void main(String[] args) {
	    System.out.println("a statement");
		SpringApplication.run(StrategoApplication.class, args);
	}

}
