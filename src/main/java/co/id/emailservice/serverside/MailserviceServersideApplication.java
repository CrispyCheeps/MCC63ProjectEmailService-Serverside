package co.id.emailservice.serverside;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MailserviceServersideApplication {

    public static void main(String[] args) {
        SpringApplication.run(MailserviceServersideApplication.class, args);
        System.out.println("Serverside is running");
    }

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

}
