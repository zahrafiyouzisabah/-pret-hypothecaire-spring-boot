package ca.uqam.mgl7460.tp3.controleurs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RestServiceApplication {

	public static void main(String[] args) {
		System.out.println("Startimg SPRING application ");
		SpringApplication.run(RestServiceApplication.class, args);
	}

}
