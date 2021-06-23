package eu.happybit.poller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
public class PollerApplication {

	public static void main(String[] args) {
		SpringApplication.run(PollerApplication.class, args);
	}

}
