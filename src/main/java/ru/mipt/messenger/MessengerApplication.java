package ru.mipt.messenger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan(basePackages = {"ru.mipt.messenger.converters", "ru.mipt.messenger.models"}) // required for chat type converter
@SpringBootApplication
public class MessengerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MessengerApplication.class, args);
		System.out.println("View Swagger docs at http://localhost:8080/swagger-ui/index.html");
	}
}