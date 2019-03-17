package tech.comfortheart.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class RestPocApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestPocApplication.class, args);
	}

}
