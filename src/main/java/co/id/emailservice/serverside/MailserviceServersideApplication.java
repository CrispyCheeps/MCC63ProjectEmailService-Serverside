package co.id.emailservice.serverside;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MailserviceServersideApplication {

	public static void main(String[] args) {
		SpringApplication.run(MailserviceServersideApplication.class, args);
		System.out.println("Serverside is running");
	}

}
