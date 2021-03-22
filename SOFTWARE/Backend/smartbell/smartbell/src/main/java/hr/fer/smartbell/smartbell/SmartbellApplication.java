package hr.fer.smartbell.smartbell;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class SmartbellApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartbellApplication.class, args);
	}

}
