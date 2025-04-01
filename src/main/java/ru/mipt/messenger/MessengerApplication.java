package ru.mipt.messenger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MessengerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MessengerApplication.class, args);
		System.out.println("View Swagger docs at http://localhost:8080/swagger-ui/index.html");
	}
}